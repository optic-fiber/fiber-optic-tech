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

import static com.cheroliv.opticfiber.ApplicationUtils.dateTimeFormattedForFileName
import static com.cheroliv.opticfiber.config.ApplicationConstants.MOTIF_END_DATE_TIME
import static com.cheroliv.opticfiber.config.ApplicationConstants.MOTIF_START_DATE_TIME


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
            @Value('${application.data.home-directory-name}')
                    String homeDirectoryName,
            //recap-spreadsheet
            @Value('${application.data.recap-spreadsheet-directory-name}')
                    recapSpreadsheetDirectoryName,
            //recap_date-time1_date-time2.xlsx
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

    /**
     *
     * ------------------------------------
     * | startDate | enDate        | case |
     * |----------------------------------|
     * |null       | null          | 1    |
     * |----------------------------------|
     * |startDate equals endDate   | 2    |
     * |----------------------------------|
     * |null       | not null      | 3    |
     * |----------------------------------|
     * |not null   | null          | 4    |
     * |----------------------------------|
     * |startDate is after endDate | 5    |
     * |----------------------------------|
     * |startDate is before endDate| 6    |
     * ------------------------------------
     * case (1): whole inters
     * case (2): case (4) : inters since start date not null to the last
     * case (3): inters from eldest to date not null
     * case (5): inverse date
     * case (6): inters since starDate to endDate
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    String generateRecapFileName(
            LocalDateTime startDate,
            LocalDateTime endDate) {
        //case 1
        if (!startDate && !endDate) return noDateInFileName()
        //case 2
        if (startDate.isEqual(endDate)) return startDateToLastInterDate(startDate)
        //case 3
        if(!startDate)return oldestInterDateToEndDate(endDate)
        //case 4
        if(!endDate)return startDateToLastInterDate(startDate)
        //case 5
        if (startDate.isAfter(endDate)) {
            LocalDateTime tmp = startDate
            startDate = endDate
            endDate = tmp
        }
        //case 6
        recapSpreadsheetFileName.replace(
                MOTIF_START_DATE_TIME,
                dateTimeFormattedForFileName(startDate))
                .replace(
                        MOTIF_END_DATE_TIME,
                        dateTimeFormattedForFileName(endDate))
    }

    String oldestInterDateToEndDate(LocalDateTime localDateTime) {
        null
    }

    String noDateInFileName() {
        null
    }

    String startDateToLastInterDate(LocalDateTime localDateTime) {
        ''
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
