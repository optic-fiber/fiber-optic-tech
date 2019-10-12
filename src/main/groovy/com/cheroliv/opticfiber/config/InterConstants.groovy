package com.cheroliv.opticfiber.config

import groovy.transform.CompileStatic

@CompileStatic
interface InterConstants {
    static String NOT_NULL_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.NotNull.message}"
    static String SIZE_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Size.message}"
    static String MIN_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Min.message}"
    static String PATTERN_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Pattern.message}"
    static String MAX_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Max.message}"

    static Integer INTER_ID_MIN_VALUE = 1
    static Long HEURE_MIN_VALUE = 8
    static Long HEURE_MAX_VALUE = 19
    static Integer PRENOM_SIZE_VALUE = 100
    static Integer NOM_SIZE_VALUE = 100

    static String INTER_ID_COLUMN_NAME = 'id_inter'

    static String INTER_ND_COLUMN_NAME = 'ND'
    static String INTER_NOM_COLUMN_NAME = 'nom'
    static String INTER_PRENOM_COLUMN_NAME = 'prenom'
    static String INTER_HEURE_COLUMN_NAME = 'heure'
    static String INTER_DATE_COLUMN_NAME = 'date'
    static String INTER_CONTRAT_COLUMN_NAME = 'contrat'
    static String INTER_TYPE_COLUMN_NAME = 'type'


    static final String ID_INTER_JSON_FIELD_NAME="id_inter"
    static final String ND_INTER_JSON_FIELD_NAME="ND"
    static final String LASTNAME_INTER_JSON_FIELD_NAME="nom"
    static final String FIRSTNAME_INTER_JSON_FIELD_NAME="prenom"
    static final String CONTRACT_INTER_JSON_FIELD_NAME="contrat"
    static final String HOUR_INTER_JSON_FIELD_NAME="heure"
    static final String DATE_INTER_JSON_FIELD_NAME="date"
    static final String TYPE_INTER_JSON_FIELD_NAME="type"

    static final String PASSAGE_DE_CABLE = "Passage de cable"

    static final String ND_SIZE_CSTRT_TPL_MSG='{com.cheroliv.fiber.inter.domain.nd.size.message}'
    static final String ND_NOTNULL_CSTRT_TPL_MSG='{com.cheroliv.fiber.inter.domain.nd.notnull.message}'

//    static final String NOT_NULL_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.NotNull.message}"
//    static final String SIZE_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Size.message}"
//    static final String MIN_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Min.message}"
//    static final String PATTERN_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Pattern.message}"
//    static final String MAX_CSTRT_TEMPLATE_MSG = "{javax.validation.constraints.Max.message}"
//    static final Integer INTER_ID_MIN_VALUE = 1
//    static final Long HEURE_MIN_VALUE = 8
//    static final Long HEURE_MAX_VALUE = 19
//    static final Integer PRENOM_SIZE_VALUE = 100
//    static final Integer NOM_SIZE_VALUE = 100
//    static final String INTER_ID_COLUMN_NAME = 'id'
//    static final String INTER_ND_COLUMN_NAME = 'nd'
//    static final String INTER_NOM_COLUMN_NAME = 'nom'
//    static final String INTER_PRENOM_COLUMN_NAME = 'prenom'
//    static final String INTER_HEURE_COLUMN_NAME = 'heure'
//    static final String INTER_DATE_COLUMN_NAME = 'date'
//    static final String INTER_CONTRAT_COLUMN_NAME = 'contrat'
//    static final String INTER_TYPE_COLUMN_NAME = 'type'
}