package com.cheroliv.opticfiber.recap.service

import com.cheroliv.opticfiber.inter.service.InterDataService
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDateTime

//import com.cheroliv.fiber.inter.repo.InterDao
//import com.cheroliv.fiber.inter.domain.InterUtils
//import com.cheroliv.opticfiber.entities.dao.InterEntity
//import com.cheroliv.fiber.inter.domain.InterDto

//import com.cheroliv.fiber.recap.model.Recap
//import com.cheroliv.fiber.recap.spreadsheet.SpreadsheetRecap

//import org.springframework.beans.factory.annotation.Value

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

//    final String classeurPathName
//    final String fiberUserDataFolderName
//    final String classeurDirectoryName
    final InterDataService service

//    @Autowired
//    void setService(InterDataService service) {
//        this.service = service
//    }
//    final InterDao repo
//    SpreadsheetRecap classeur
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
    RecapServiceImp(
//            @Value('${application.data.spreadsheet-file-name}')
//                    String recapSpreadsheetFileName,
//            @Value('${application.data.home-directory-name}')
//                    String homeDirectoryName,
//            @Value('${application.data.home-spreadsheet-directory-name}')
//                    String recapSpreadsheetDirectoryName,
InterDataService service
//,
//            InterDao repo
    ) {
//        this.repo = repo
        this.service = service
//        this.classeurPathName = recapSpreadsheetFileName
//        this.fiberUserDataFolderName = homeDirectoryName
//        this.classeurDirectoryName = recapSpreadsheetDirectoryName
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

    String generateRecapFileName(
            LocalDateTime startDate,
            LocalDateTime endDate) {
        ''
    }

//
//
//    @Override
//    @Transactional(readOnly = true)
//    SpreadsheetRecap init() {
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
//
//        this.classeur = new SpreadsheetRecap(
//                classeurPathName: strRecapPath,
//                nbFeuille: service.countMois(),
//                nomFeuilles: this.nomFeuilles(),
//                moisParAnnee: service.findAllMoisFormatFrParAnnee())
//        this.classeur
//    }
//
//    static String getSeparator() {
//        Paths.get(System.getProperty("user.home"))
//                .fileSystem.separator
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    Recap processRecap(String nomFeuilles, Integer moisInt, Integer anneeIntValue) {
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
//                                "${InterUtils.convertNombreEnMois moisInt}" +
//                                " $anneeIntValue",
//                labelCurrentMonthYearFormattedFr:
//                        InterUtils.convertNombreEnMois(moisInt))
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    SpreadsheetRecap processFeuilles() {
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
//    }
//
//    @Override
//    SpreadsheetRecap processClasseurFeuilles(String classeurPath) {
//        this.path = classeurPath
//        this.processFeuilles()
//        this.classeur.createExcelWorkBook()
//        classeur
//    }
}
