package com.cheroliv.opticfiber.recap.service


import com.cheroliv.opticfiber.inter.repository.InterRepository
import com.cheroliv.opticfiber.inter.service.InterDataService
import com.cheroliv.opticfiber.recap.model.Recap
import com.cheroliv.opticfiber.recap.spreadsheet.SpreadsheetRecap
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static com.cheroliv.opticfiber.config.ApplicationConstants.KEY_SYSTEM_PROPERTY_FILE_SEPARATOR

//import com.cheroliv.fiber.inter.repo.InterDao
//import com.cheroliv.fiber.inter.domain.ApplicationUtils
//import com.cheroliv.opticfiber.entities.dao.InterEntity
//import com.cheroliv.fiber.inter.domain.InterDto

//import com.cheroliv.fiber.recap.model.Recap
//import com.cheroliv.fiber.recap.spreadsheet.SpreadsheetRecap


//
//import javax.validation.constraints.NotEmpty
//import javax.validation.constraints.NotNull
//import java.nio.file.Paths
//
@Slf4j
@Service
@TypeChecked
@Transactional(readOnly = true)
class RecapServiceImp implements RecapService {


//    @Autowired
//    void setService(InterDataService service) {
//        this.service = service
//    }
//    final InterDao repo
    SpreadsheetRecap classeur
//    @NotNull
//    @NotEmpty
//    String path
//
//    @Override
//    void setPath(String path) {
//        this.path = path
//    }
//
//
//    /*
//    @Value('${application.data.home-directory-name}')
//    String homeDirectoryName
//     */
    final String homeDirectoryName
    final String recapSpreadsheetDirectoryName
    final String recapSpreadsheetFileName
    final InterDataService service
    final InterRepository repo

    RecapServiceImp(
            //.fiber-simple
            @Value('$application.data.home-directory-name')
                    String homeDirectoryName,
            //recap-spreadsheet
            @Value('$application.data.recap-spreadsheet-directory-name')
                    recapSpreadsheetDirectoryName,
            //recap_date-time1_date-time2.xlsx
            @Value('$application.data.recap-spreadsheet-file-name')
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

    static String dateTimeFormattedForFileName(LocalDateTime dateTime) {
        String strDate = dateTime.format(DateTimeFormatter
                .ofPattern("yyyy-MM-dd"))
        String strTime = dateTime.format(DateTimeFormatter
                .ofPattern("HH:mm"))
        String strDateTime = "${strDate}_${strTime}"
        strDateTime
    }

    @Override
    String generateRecapFileName(
            LocalDateTime startDate,
            LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            def tmp = startDate
            startDate = endDate
            endDate = tmp
        }
        String recapFileName = recapSpreadsheetFileName
        recapFileName.replace(
                'time1_date',
                dateTimeFormattedForFileName(startDate))
        recapFileName.replace(
                'time2_date',
                dateTimeFormattedForFileName(endDate))
        recapFileName
    }


    @Override
    @Transactional(readOnly = true)
    SpreadsheetRecap init() {
//        String strRecapPath = path +//arg
//                separator +
//                fiberUserDataFolderName +//.fiber
//                separator +
//                classeurDirectoryName +//output
//                separator +
//                classeurPathName//recapClasseur.xlsx
//
//        File g = new File(path +
//                separator +
//                fiberUserDataFolderName)//.fiber
//        g.exists() && g.directory ?: g.mkdir()
//
//        File i = new File(path +
//                separator +
//                fiberUserDataFolderName +
//                separator +
//                classeurDirectoryName)//output
//        i.exists() && i.directory ?: i.mkdir()
//
//        File f = new File(strRecapPath)
//        f.exists() ?: f.createNewFile()//recapClasseur.xlsx
//
        this.classeur = new SpreadsheetRecap()
//        this.classeur = new SpreadsheetRecap(
//                classeurPathName: strRecapPath,
//                nbFeuille: service.countMois(),
//                nomFeuilles: this.nomFeuilles(),
//                moisParAnnee: service.findAllMoisFormatFrParAnnee())
        this.classeur
    }

    @Override
    @Transactional(readOnly = true)
    Recap processRecap(String nomFeuilles, Integer moisInt, Integer anneeIntValue) {
//        new Recap(
//                sheetName: nomFeuilles,
//                inters: repo.findAllDeMoisDansAnnee(
//                        moisInt, anneeIntValue).collect {
//                    InterEntity it ->
//                        new InterDto(it)
//                },
//                annee: anneeIntValue,
//                mois: moisInt,
//                nbInterTotal: repo
//                        .countInterParMoisDansAnnee(
//                                moisInt, anneeIntValue),
//                nbBaocBaap: repo
//                        .countRacParMoisDansAnnee(
//                                moisInt, anneeIntValue),
//                nbBafa: repo
//                        .countBafaParMoisDansAnnee(
//                                moisInt, anneeIntValue),
//                nbBast: repo
//                        .countBastParMoisDansAnnee(
//                                moisInt, anneeIntValue),
//                nbPlp: repo
//                        .countPlpParMoisDansAnnee(
//                                moisInt, anneeIntValue),
//                nbSav: repo
//                        .countSavParMoisDansAnnee(
//                                moisInt, anneeIntValue),
//                nbPdcTotal: repo
//                        .countPdcParMoisDansAnnee(
//                                moisInt, anneeIntValue),
//                nbPdcBafa: repo
//                        .countPdcBafaParMoisDansAnnee(
//                                moisInt, anneeIntValue),
//                nbPdcBast: repo
//                        .countPdcBastParMoisDansAnnee(
//                                moisInt, anneeIntValue),
//                nbPdcBaocBaap: repo
//                        .countPdcBaocBaapParMoisDansAnnee(
//                                moisInt, anneeIntValue),
//                labelTitreRecap:
//                        "${Recap.PRE_LABEL_TITRE_RECAP}" +
//                                "${ApplicationUtils.convertNombreEnMois moisInt}" +
//                                " $anneeIntValue",
//                labelCurrentMonthYearFormattedFr:
//                        ApplicationUtils.convertNombreEnMois(moisInt))
        new Recap()
    }
//
    @Override
    @Transactional(readOnly = true)
    SpreadsheetRecap processFeuilles() {
//        init()
//        List<List<Integer>> listIntMoisAnnee =
//                repo.distinctMoisParAnnee()
//        classeur.recaps = new ArrayList<Recap>(classeur.nbFeuille)
//        assert classeur.nbFeuille == classeur.moisParAnnee.size()
//        assert classeur.nbFeuille == listIntMoisAnnee.size()
//
//        if (classeur.nbFeuille == null || classeur.nbFeuille <= 0) {
//            classeur.recaps = new ArrayList<Recap>()
//            classeur.nbFeuille = 0
//        } else {
//            classeur.moisParAnnee.eachWithIndex {
//                Map<String, Integer> map, int idx ->
//                    String moisStrKey = map.keySet().first()
//                    Integer anneeIntValue = map.get(moisStrKey)
//                    Integer moisInt = listIntMoisAnnee.get(idx).first()
//                    classeur.recaps.add idx,
//                            processRecap(classeur
//                                    .nomFeuilles
//                                    .get(idx),
//                                    moisInt,
//                                    anneeIntValue)
//            }
//        }
//        classeur
        new SpreadsheetRecap()
    }
//
    @Override
    SpreadsheetRecap processClasseurFeuilles(String classeurPath) {
//        this.path = classeurPath
//        this.processFeuilles()
//        this.classeur.createExcelWorkBook()
//        classeur
        new SpreadsheetRecap()
    }
}
