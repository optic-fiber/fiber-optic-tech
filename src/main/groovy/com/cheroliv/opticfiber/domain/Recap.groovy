package com.cheroliv.opticfiber.domain

import com.cheroliv.opticfiber.inter.domain.InterDto
import groovy.transform.ToString

import java.time.LocalDateTime


@ToString
class Recap implements Serializable {
    String sheetName
    List<InterDto> inters
    Integer annee
    Integer mois
    Integer nbInterTotal
    Integer nbBaocBaap
    Integer nbBafa
    Integer nbBast
    Integer nbPlp
    Integer nbSav
    Integer nbPdcTotal
    Integer nbPdcBafa
    Integer nbPdcBast
    Integer nbPdcBaocBaap
    LocalDateTime startDate
    LocalDateTime endDate
    String labelCurrentMonthYearFormattedFr
    String labelTitreRecap


    static final String PRE_LABEL_TITRE_RECAP = "Récapitulatif du mois : "
    static final String labelDefPdc = "PDC = passage de cable"
    static final String labelNbInterTotal = "Nombre d'inter total"
    static final String labelNbBaocBaap = "Nombre de BAOC & BAAP"
    static final String labelNbBafa = "Nombre de BAFA"
    static final String labelNbBast = "Nombre de BAST"
    static final String labelNbPlp = "Nombre de PLP"
    static final String labelNbSav = "Nombre de SAV"
    static final String labelNbPdcTotal = "Nombre total de passage de cable"
    static final String labelNbPdcBafa = "Nombre de passage de cable en BAFA"
    static final String labelNbPdcBast = "Nombre de passage de cable en BAST"
    static final String labelNbPdcBaocBaap = "Nombre de passage de cable en BAOC BAAP"
    static final String labelListInter = "Liste des interventions"
    static final String labelNd = "ND"
    static final String labelType = "type"
    static final String labelContrat = "contrat"
    static final String labelHeure = "heure"
    static final String labelDate = "date"
    static final String labelNom = "nom"
    static final String labelPrenom = "prénom"
}
