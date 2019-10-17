package com.cheroliv.opticfiber

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

import java.nio.charset.StandardCharsets
import java.time.LocalDateTime

import static com.cheroliv.opticfiber.ApplicationUtils.*

@Slf4j
@CompileStatic
class CheckDataValues {


//    //the data  file
    String dataSetFileRelativePath =
            'src/test/resources/inters_full.json'

    // the data file content
    String dataSetText = getFileTextOnproject(dataSetFileRelativePath)

    // Slurp(parse) the content
    List<Map<String, String>> dataListMap =
            (new JsonSlurper().parseText(dataSetText)
                    as List<Map<String, String>>)

    // build a list of localdates
    List<LocalDateTime> dates = dataListMap.collect {
        Map<String, String> it ->
            buildDateTime(it.get('date'), it.get('heure'))
    }

    // build a map with index as key and date as value
    Map<Integer, LocalDateTime> datesIndexedMap = dates
            .withIndex()
            .collectEntries() {
                LocalDateTime it, Integer idx ->
                    [(idx): it]
            }

    // group by dates not disctinct, with date as key and map as value
    // this map is the index of date from the original content,
    // and the value is the date
    Map<LocalDateTime, Map<Integer, LocalDateTime>> duplicateDatesMap =
            datesIndexedMap.groupBy {
                Map.Entry<Integer, LocalDateTime> it ->
                    it.value
            }.findAll {
                Map.Entry<LocalDateTime, Map<Integer, LocalDateTime>> it ->
                    it.value.size() > 1
            }

    List<Set<Integer>> duplicateDatesGroupedByIndex =
            duplicateDatesMap.collect {
                Map.Entry<LocalDateTime, Map<Integer, LocalDateTime>> it ->
                    it.value.keySet()
            }


    static String getFileTextOnproject(String relativePath) {
        File file = new File(
                new File(".").canonicalPath +
                        separator +
//                        System.getProperty(KEY_SYSTEM_PROPERTY_FILE_SEPARATOR) +
                        relativePath)
        assert file.exists() && file.file && !file.directory
        file.getText(StandardCharsets.UTF_8.name())
    }


    static LocalDateTime buildDateTime(String strDate, String strHour) {
        LocalDateTime.of(
                parseStringDateToLocalDate(strDate),
                parseStringHeureToLocalTime(strHour))
    }

    Boolean isTimeExistsInDataset(LocalDateTime dateTime) {
        dates.contains(dateTime)
    }

    Boolean isMinusMoreThen8ExistsInDataset(LocalDateTime dateTime) {
        def minus = dateTime.minusHours(1)
        if (minus.hour > 8)
            dates.contains(dateTime.minusHours(1))
        false
    }

    Boolean isPlusLessThan19ExistsInDataset(LocalDateTime dateTime) {
        def plus = dateTime.plusHours(1)
        if (plus.hour < 19)
            dates.contains(dateTime.plusHours(1))
        false
    }

    @Test
    @DisplayName('test_dates_are_unique_in_dataset')
    void test_dates_are_unique_in_dataset() {
        assert duplicateDatesGroupedByIndex.empty
    }
}