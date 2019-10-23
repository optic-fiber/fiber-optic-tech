package com.cheroliv.opticfiber.recap.spreadsheet

import com.cheroliv.opticfiber.inter.domain.InterDto
import com.cheroliv.opticfiber.recap.model.Recap
import com.jameskleeh.excel.ExcelBuilder
import groovy.transform.ToString
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Slf4j
@ToString
@TypeChecked
class SpreadsheetRecap implements Serializable {
    XSSFWorkbook classeur
    String classeurPathName
    Integer nbFeuille
    List<String> nomFeuilles
    List<Recap> recaps
    List<Map<String, Integer>> moisParAnnee
    LocalDateTime startDate
    LocalDateTime endDate

    void createExcelWorkBook() {
        classeurPathName
        classeur
        try {
            File classeurFile
            classeurFile = new File(classeurPathName)
            try {
                FileOutputStream fos
                fos = new FileOutputStream(classeurFile)
                classeur = ExcelBuilder.build {
                    XSSFWorkbook workbook ->
                        recaps.eachWithIndex { Recap recap, int idx ->
                            sheet(nomFeuilles.get(idx)) {
                                row {
                                    merge {
                                        String titre = recap.labelTitreRecap
                                        cell(titre)
                                        skipCells(4)
                                    }
                                }
                                row()
                                row {
                                    cell(recap.labelNbInterTotal)
                                    cell(recap.nbInterTotal)
                                    cell()
                                    merge {
                                        cell(recap.labelDefPdc)
                                        skipCells(2)
                                    }
                                }
                                row {
                                    cell(recap.labelNbBaocBaap)
                                    cell(recap.nbBaocBaap)
                                }
                                row {
                                    cell(recap.labelNbBafa)
                                    cell(recap.nbBafa)
                                }
                                row {
                                    cell(recap.labelNbPdcTotal)
                                    cell(recap.nbPdcTotal)
                                }
                                row {
                                    cell(recap.labelNbBast)
                                    cell(recap.nbBast)
                                }
                                row {
                                    cell(recap.labelNbPdcBafa)
                                    cell(recap.nbPdcBafa)
                                }
                                row {
                                    cell(recap.labelNbPlp)
                                    cell(recap.nbPlp)
                                }
                                row {
                                    cell(recap.labelNbPdcBast)
                                    cell(recap.nbPdcBast)
                                }
                                row {
                                    cell(recap.labelNbSav)
                                    cell(recap.nbSav)
                                }
                                row {
                                    cell(recap.labelNbPdcBaocBaap)
                                    cell(recap.nbPdcBaocBaap)
                                }
                                row()
                                row(recap.labelListInter)
                                row {
                                    cell(recap.labelNd)
                                    cell(recap.labelType)
                                    cell(recap.labelContrat)
                                    cell(recap.labelHeure)
                                    cell(recap.labelDate)
                                    cell recap.labelNom
                                    cell recap.labelPrenom
                                }
                                recap.inters.each { InterDto i ->
                                    row {
                                        cell i.nd
                                        cell i.typeInter
                                        cell i.contract
                                        cell i.dateTime.format(DateTimeFormatter
                                                .ofPattern("dd-MM-yyyy"))
                                        cell i.dateTime.format(DateTimeFormatter
                                                .ofPattern("HH:mm"))
                                        cell i.firstName
                                        cell i.lastName
                                    }
                                }
                            }
                            workbook.getSheetAt(idx).autoSizeColumn 0
                            workbook.getSheetAt(idx).autoSizeColumn 3
                            workbook.getSheetAt(idx).autoSizeColumn 5
                            workbook.getSheetAt(idx).autoSizeColumn 6
                        }

                }
                classeur.write fos
                fos.close()
            } catch (Exception e) {
                e.printStackTrace()
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
    }
}
