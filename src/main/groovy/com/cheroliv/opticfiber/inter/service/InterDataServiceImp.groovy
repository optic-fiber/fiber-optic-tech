package com.cheroliv.opticfiber.inter.service

import com.cheroliv.opticfiber.inter.domain.InterDto
import com.cheroliv.opticfiber.inter.domain.enumeration.ContractEnum
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import com.cheroliv.opticfiber.inter.repository.InterRepository
import com.cheroliv.opticfiber.inter.service.exception.InterNotFoundException
import com.cheroliv.opticfiber.inter.service.exception.InterTypeEnumException
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import groovy.transform.TypeChecked
import groovy.transform.TypeCheckingMode
import groovy.util.logging.Slf4j
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static com.cheroliv.opticfiber.ApplicationUtils.*
import static com.cheroliv.opticfiber.config.ApplicationConstants.KEY_DATA_HOME_DIRECTORY
import static com.cheroliv.opticfiber.config.ApplicationConstants.KEY_DATA_JSON_BACKUP_FILE_NAME
import static com.cheroliv.opticfiber.config.InterConstants.*

@Slf4j
@Service
@TypeChecked
@Transactional(readOnly = true)
class InterDataServiceImp implements InterDataService {

    final String homeDirectoryName
    final String jsonBackupFileName
    final InterRepository interRepository

    InterDataServiceImp(
            @Value(KEY_DATA_HOME_DIRECTORY)
                    String homeDirectoryName,
            @Value(KEY_DATA_JSON_BACKUP_FILE_NAME)
                    String jsonBackupFileName,
            InterRepository interRepository) {
        this.interRepository = interRepository
        this.homeDirectoryName = homeDirectoryName
        this.jsonBackupFileName = jsonBackupFileName
    }

    private void createHomeDataDirectory() {
        File file = new File(getUserHomePath() +
                getSeparator() +
                homeDirectoryName)
        if (file.exists()) {
            if (file.isFile()) {
                file.delete()
                file.mkdir()
            }
        } else file.mkdir()
    }

    private void createJsonFile() {
        createHomeDataDirectory()
        File file = new File(getUserHomePath() +
                getSeparator() +
                homeDirectoryName +
                getSeparator() +
                this.jsonBackupFileName
        )
        if (!(file.exists() && file.isFile()))
            if (file.exists() && file.isDirectory()) {
                file.deleteDir()
                file.createNewFile()
            } else file.createNewFile()
    }

    @Override
    String getJsonBackupFilePath() {
        createHomeDataDirectory()
        createJsonFile()
        getUserHomePath() +
                getSeparator() +
                this.homeDirectoryName +
                getSeparator() +
                this.jsonBackupFileName
    }


    @Override
    InterDto find(String nd, String type) {
        if (!TypeInterEnum.values().collect {
            it.name()
        }.contains(type)) throw new InterTypeEnumException(type)
        Optional<InterDto> result = interRepository.find(
                nd, TypeInterEnum.valueOfName(type))
        if (result.present)
            result.get()
        else throw new InterNotFoundException(nd, type)
    }

    @Override
    Integer countMois(LocalDateTime startDate , LocalDateTime endDate) {
        interRepository.distinctMoisParAnnee()?.size() ?: 0
    }
    @Override
    Integer countMois() {
        interRepository.distinctMoisParAnnee()?.size() ?: 0
    }

    @Override
    List<Map<String, Integer>> findAllMoisFormatFrParAnnee() {
        List<List<Integer>> result =
                interRepository.distinctMoisParAnnee()
        List<Map<String, Integer>> finalResult =
                new ArrayList<Map<String, Integer>>(result.size())
        result.eachWithIndex { List<Integer> item, int idx ->
            Integer intMois = item.get(0)
            Integer annee = item.get(1)
            Map<String, Integer> map = new HashMap<String, Integer>(1)
            map[convertNombreEnMois(intMois)] = annee
            finalResult.add(idx, map)
        }
        finalResult
    }

    @Override
    List<Map<String, Integer>> findAllMoisFormatFrParAnnee(
            LocalDateTime startDate, LocalDateTime endDate) {
        List<List<Integer>> result =
                interRepository.distinctMoisParAnnee(
                        startDate,endDate)
        List<Map<String, Integer>> finalResult =
                new ArrayList<Map<String, Integer>>(result.size())
        result.eachWithIndex { List<Integer> item, int idx ->
            Integer intMois = item.get(0)
            Integer annee = item.get(1)
            Map<String, Integer> map = new HashMap<String, Integer>(1)
            map[convertNombreEnMois(intMois)] = annee
            finalResult.add(idx, map)
        }
        finalResult
    }

    private static LocalDateTime buildDateTime(String strDate, String strHour) {
        LocalDateTime.of(
                parseStringDateToLocalDate(strDate),
                parseStringHeureToLocalTime(strHour))
    }

    private static ContractEnum parseI18nInterContractEnum(String i18nContactValue) {
        ContractEnum.valueOfName(
                i18nContactValue ==
                        //TODO:change with a message bean handling i18n
                        'Passage de cable' ?
                        ContractEnum.CABLE_ROUTING.name() :
                        i18nContactValue)
    }

    private void importJson(Map<String, String> it) {
        interRepository.find(
                it[ND_INTER_JSON_FIELD_NAME],
                TypeInterEnum.valueOfName(
                        it[TYPE_INTER_JSON_FIELD_NAME])) ?:
                interRepository.save(new InterDto(
                        nd: it[ND_INTER_JSON_FIELD_NAME],
                        lastName: it[LASTNAME_INTER_JSON_FIELD_NAME],
                        firstName: it[FIRSTNAME_INTER_JSON_FIELD_NAME],
                        dateTime: buildDateTime(
                                it[DATE_INTER_JSON_FIELD_NAME],
                                it[HOUR_INTER_JSON_FIELD_NAME]),
                        contract: parseI18nInterContractEnum(
                                it[CONTRACT_INTER_JSON_FIELD_NAME]).name(),
                        typeInter: TypeInterEnum.valueOfName(it[TYPE_INTER_JSON_FIELD_NAME]).name()))

    }


    @Transactional
    void importJsonFromFile() {
        assert new File(getJsonBackupFilePath()).exists()
        if (!(new File(getJsonBackupFilePath()).text == null
                || new File(getJsonBackupFilePath()).text == ""
                || new File(getJsonBackupFilePath()).text.empty)) {
            Object jsonInters = new JsonSlurper()
                    .parseText(new File(getJsonBackupFilePath())
                            .getText(StandardCharsets.UTF_8.name()))
            (jsonInters as Collection).empty ?:
                    (jsonInters as List<Map<String, String>>)
                            .each { Map<String, String> it ->
                                if (!it.isEmpty()) this.importJson(it)
                            }
        }
    }

    @Override
    @TypeChecked(TypeCheckingMode.SKIP)
    String buildJsonInter(InterDto inter) {
        JsonBuilder builder = new JsonBuilder()
        String result =
                builder {
                    '"id_inter"' "\"${inter.id}\""
                    '"ND"' "\"${inter.nd}\""
                    '"nom"' "\"${inter.lastName}\""
                    '"prenom"' "\"${inter.firstName}\""
                    '"heure"' inter.dateTime.getHour() > 9 ?
                            "\"${inter.dateTime.getHour()}:00:00\"" :
                            "\"0${inter.dateTime.getHour()}:00:00\""
                    '"date"' "\"${inter.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}\""
                    '"contrat"' "\"${inter.contract}\""
                    '"type"' "\"${inter.typeInter}\""
                }.toString()
        "{${result.substring(1, result.size() - 1)}}".toString()
    }


    @Override
    void saveToJsonFile() {
        List<String> jsonList = new ArrayList<String>()

        //building json
        interRepository.findAll().each { InterDto inter ->
            jsonList.add "${buildJsonInter(inter)}\n".toString()
        }

        //renaming file
        String ajout = new SimpleDateFormat("yyyyMMddHHmmss")
                .format new Date()
        new File(this.getJsonBackupFilePath()).renameTo(
                "${FilenameUtils.removeExtension this.getJsonBackupFilePath()}" +
                        "${ajout}.json")
        //saving to file
        File jsonBackUpFile = new File(this.getJsonBackupFilePath())
        jsonBackUpFile.createNewFile()
        jsonBackUpFile.setText(jsonList.toListString(), StandardCharsets.UTF_8.toString())
    }
}


