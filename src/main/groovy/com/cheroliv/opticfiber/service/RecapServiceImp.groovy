package com.cheroliv.opticfiber.service


import com.cheroliv.opticfiber.repository.InterRepository
import com.cheroliv.opticfiber.domain.Recap
import com.cheroliv.opticfiber.service.exceptions.RecapNoInterInRepositoryException
import com.cheroliv.opticfiber.view.SpreadsheetRecap
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDateTime

import static com.cheroliv.opticfiber.util.ApplicationUtils.*
import static com.cheroliv.opticfiber.config.ApplicationConstants.*

@Slf4j
@Service
@TypeChecked
@Transactional(readOnly = true)
class RecapServiceImp implements RecapService {

//    SpreadsheetRecap classeur
    final String homeDirectoryName
    final String recapSpreadsheetDirectoryName
    final String recapSpreadsheetFileName
    final InterDataService service
    final InterRepository repo

    RecapServiceImp(
            @Value('${application.data.home-directory-name}')
                    String homeDirectoryName,
            @Value('${application.data.recap-spreadsheet-directory-name}')
                    recapSpreadsheetDirectoryName,
            @Value('${application.data.recap-spreadsheet-file-name}')
                    String recapSpreadsheetFileName,
            InterDataService service,
            InterRepository repo) {
        this.repo = repo
        this.service = service
        this.homeDirectoryName = homeDirectoryName
        this.recapSpreadsheetDirectoryName = recapSpreadsheetDirectoryName
        this.recapSpreadsheetFileName = recapSpreadsheetFileName
    }


    @Override
    SpreadsheetRecap processClasseurFeuilles(LocalDateTime startDate, LocalDateTime endDate) {
        List<LocalDateTime> consistentDates =
                evaluateRecapDates(startDate, endDate)
        startDate = consistentDates.first()
        endDate = consistentDates.last()
        processFeuilles(startDate, endDate)
    }
    @Override
    SpreadsheetRecap processClasseurFeuilles(){
        this.processClasseurFeuilles(null,null)
    }

    /*

@Override
void run(String... strings) throws Exception {
    interService.setUp()
    interService.importFromJsonFile interService.fiberJsonFilePath
    interService.processClasseurFeuilles()
    interService.saveToJsonFile interService.fiberJsonFilePath
}
 */


    @Override
    @Transactional(readOnly = true)
    SpreadsheetRecap processFeuilles(){
        this.processFeuilles(null,null)
    }

    @Override
    @Transactional(readOnly = true)
    SpreadsheetRecap processFeuilles(LocalDateTime startDate, LocalDateTime endDate) {
        SpreadsheetRecap classeur = init(startDate, endDate)
        List<List<Integer>> listIntMoisAnnee =
                repo.distinctMoisParAnnee(startDate, endDate)
        classeur.recaps = new ArrayList<Recap>(classeur.nbFeuille)
        assert classeur.nbFeuille == classeur.moisParAnnee.size()
        assert classeur.nbFeuille == listIntMoisAnnee.size()

        if (classeur.nbFeuille == null || classeur.nbFeuille <= 0) {
            classeur.recaps = new ArrayList<Recap>()
            classeur.nbFeuille = 0
        } else {
            classeur.moisParAnnee.eachWithIndex {
                Map<String, Integer> map, int idx ->
                    String moisStrKey = map.keySet().first()
                    Integer anneeIntValue = map.get(moisStrKey)
                    Integer moisInt = listIntMoisAnnee.get(idx).first()
                    classeur.recaps.add idx,
                            processRecap(classeur
                                    .nomFeuilles
                                    .get(idx),
                                    moisInt,
                                    anneeIntValue)
            }
        }
        classeur.createExcelWorkBook()
        classeur
    }

    @Override
    @Transactional(readOnly = true)
    SpreadsheetRecap init(LocalDateTime startDate, LocalDateTime endDate) {
        String strRecapPath = userHomePath + separator +
                homeDirectoryName + separator +
                recapSpreadsheetDirectoryName + separator +
                generateRecapFileName(startDate, endDate)
        SpreadsheetRecap classeur = new SpreadsheetRecap(
                classeurPathName: strRecapPath,
                nbFeuille: service.countMois(startDate, endDate),
                nomFeuilles: nomFeuilles(startDate, endDate),
                moisParAnnee: service.findAllMoisFormatFrParAnnee(startDate, endDate),
                startDate: startDate,
                endDate: endDate)
        classeur
    }

    @Override
    @Transactional(readOnly = true)
    SpreadsheetRecap init() {
        this.init(null,null)
    }


    List<String> nomFeuilles(LocalDateTime startDate, LocalDateTime endDate) {
        List<Map<String, Integer>> list =
                service.findAllMoisFormatFrParAnnee(
                        startDate, endDate)
        if (list.empty) return []
        list.collect {
            Map<String, Integer> it ->
                String key = it.keySet().first()
                String value = it.get(key)
                key + SPACE_CHAR + value
        }
    }

    @Override
    List<String> nomFeuilles() {
        List<Map<String, Integer>> list =
                service.findAllMoisFormatFrParAnnee()
        List<String> finalList = new ArrayList()
        if (list.empty) return finalList
        list.eachWithIndex { item, idx ->
            String key = (item as Map<String, Integer>)
                    .keySet().first()
            String value = (item as Map<String, Integer>)
                    .get(key)
            String monthYearLabel = "$key $value"
            finalList.add idx, monthYearLabel
        }
        finalList
    }

    void checkInterRepositoryConsistent() {
        List<LocalDateTime> result = this.repo.findAllDates()
        if (!result || result?.empty)
            throw new RecapNoInterInRepositoryException()
    }

    static Boolean isDateElementOfDatesData(
            LocalDateTime dateTime,
            List<LocalDateTime> datesInDb) {
        if (!datesInDb || datesInDb?.empty)
            return false
        LocalDateTime dateMin = datesInDb.min()
        LocalDateTime dateMax = datesInDb.max()
        (dateTime.isBefore(dateMax) && dateTime.isAfter(dateMin)) ||
                dateTime.isEqual(dateMax) ||
                dateTime.isEqual(dateMin)
    }

    List<LocalDateTime> evaluateRecapDates(
            LocalDateTime startDate,
            LocalDateTime endDate) {
        checkInterRepositoryConsistent()
        List<LocalDateTime> dates = repo.findAllDates()
        LocalDateTime oldestDate = dates.min()
        LocalDateTime latestDate = dates.max()
        if (!startDate && isDateElementOfDatesData(endDate, dates))
            return [oldestDate, endDate]
        if (!endDate && isDateElementOfDatesData(startDate, dates))
            return [startDate, latestDate]
        if (!isDateElementOfDatesData(startDate, dates) ||
                !isDateElementOfDatesData(endDate, dates))
            return [oldestDate, latestDate]
        [startDate, endDate]
    }

    @Override
    String generateRecapFileName(
            LocalDateTime startDate,
            LocalDateTime endDate) {
        List<LocalDateTime> recapDateRange =
                evaluateRecapDates(startDate, endDate)
        buildRecapFileName(
                recapDateRange.first(),
                recapDateRange.last())
    }

    String buildRecapFileName(
            LocalDateTime startDate,
            LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            LocalDateTime tmp = startDate
            startDate = endDate
            endDate = tmp
        }
        recapSpreadsheetFileName.replace(
                MOTIF_START_DATE_TIME,
                dateTimeFormattedForFileName(startDate))
                .replace(
                        MOTIF_END_DATE_TIME,
                        dateTimeFormattedForFileName(endDate))
    }


    @Override
//TODO:recoder et repenser avec startDate et enddate
    @Transactional(readOnly = true)
    Recap processRecap(
            String nomFeuilles,
            Integer moisInt,
            Integer anneeIntValue) {
        new Recap(
                sheetName: nomFeuilles,
                inters: repo.findAllDeMoisDansAnnee(
                        moisInt, anneeIntValue),
                annee: anneeIntValue,
                mois: moisInt,
                nbInterTotal: repo
                        .countInterParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbBaocBaap: repo
                        .countRacParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbBafa: repo
                        .countBafaParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbBast: repo
                        .countBastParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbPlp: repo
                        .countPlpParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbSav: repo
                        .countSavParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbPdcTotal: repo
                        .countPdcParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbPdcBafa: repo
                        .countPdcBafaParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbPdcBast: repo
                        .countPdcBastParMoisDansAnnee(
                                moisInt, anneeIntValue),
                nbPdcBaocBaap: repo
                        .countPdcBaocBaapParMoisDansAnnee(
                                moisInt, anneeIntValue),
                labelTitreRecap:
                        "${Recap.PRE_LABEL_TITRE_RECAP}" +
                                "${convertNombreEnMois moisInt}" +
                                " $anneeIntValue",
                labelCurrentMonthYearFormattedFr:
                        convertNombreEnMois(moisInt))
    }


}
