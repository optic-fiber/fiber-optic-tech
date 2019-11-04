package com.cheroliv.opticfiber.config


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

interface ApplicationDefaults {
    interface Data {
        String homeDirectoryName = 'fiber-simple'
        String jsonBackupFileName = 'inters.json'
        String recapSpreadsheetDirectoryName = 'recap-spreadsheet'
        String recapSpreadsheetFileName = 'recap_date-time1_date-time2.xlsx'
    }
}

@Component
@ConfigurationProperties(
        prefix = 'application',
        ignoreUnknownFields = false)
class ApplicationProperties {

    final Data data = new Data()

    static class Data {
        String homeDirectoryName = ApplicationDefaults
                .Data
                .homeDirectoryName
        String jsonBackupFileName = ApplicationDefaults
                .Data
                .jsonBackupFileName
        String recapSpreadsheetDirectoryName = ApplicationDefaults
                .Data
                .recapSpreadsheetDirectoryName
        String recapSpreadsheetFileName = ApplicationDefaults
                .Data
                .recapSpreadsheetFileName

    }
}


final class ApplicationConstants {
    public final static String KEY_DATA_HOME_DIRECTORY = '${application.data.home-directory-name}'
    public static final String KEY_DATA_JSON_BACKUP_FILE_NAME = '${application.data.json-backup-file-name}'
    public static final String KEY_SYSTEM_PROPERTY_FILE_SEPARATOR = 'file.separator'
    public static final String KEY_SYSTEM_PROPERTY_USER_HOME = 'user.home'
    public static final String MOTIF_START_DATE_TIME = 'date-time1'
    public static final String MOTIF_END_DATE_TIME = 'date-time2'
    public static final String DATE_PATTERN_FORMAT = "yyyy-MM-dd"
    public static final String TIME_PATTERN_FORMAT = "HH:mm"
    public static final String SPACE_CHAR = ' '
    public static final String ADMIN = "ROLE_ADMIN"
    public static final String USER = "ROLE_USER"
    public static final String ANONYMOUS = "ROLE_ANONYMOUS"

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


    static final String ID_INTER_JSON_FIELD_NAME = "id_inter"
    static final String ND_INTER_JSON_FIELD_NAME = "ND"
    static final String LASTNAME_INTER_JSON_FIELD_NAME = "nom"
    static final String FIRSTNAME_INTER_JSON_FIELD_NAME = "prenom"
    static final String CONTRACT_INTER_JSON_FIELD_NAME = "contrat"
    static final String HOUR_INTER_JSON_FIELD_NAME = "heure"
    static final String DATE_INTER_JSON_FIELD_NAME = "date"
    static final String TYPE_INTER_JSON_FIELD_NAME = "type"

    static final String PASSAGE_DE_CABLE = "Passage de cable"

    static final String ND_SIZE_CSTRT_TPL_MSG = '{com.cheroliv.fiber.inter.domain.nd.size.message}'
    static final String ND_NOTNULL_CSTRT_TPL_MSG = '{com.cheroliv.fiber.inter.domain.nd.notnull.message}'

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
    static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure"
    static final String ERR_VALIDATION = "error.validation"
    static final String PROBLEM_BASE_URL = "https://localhost:8080/problem"
    static final URI DEFAULT_TYPE = URI.create(PROBLEM_BASE_URL + "/problem-with-message")
    static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(PROBLEM_BASE_URL + "/constraint-violation")
    static final URI ENTITY_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/entity-not-found")
    static final URI INVALID_PASSWORD_TYPE = URI.create(PROBLEM_BASE_URL + "/invalid-password")
    static final URI EMAIL_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/email-already-used")
    static final URI LOGIN_ALREADY_USED_TYPE = URI.create(PROBLEM_BASE_URL + "/login-already-used")
    static final URI EMAIL_NOT_FOUND_TYPE = URI.create(PROBLEM_BASE_URL + "/email-not-found")

}