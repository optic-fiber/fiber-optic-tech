package com.cheroliv.opticfiber

import com.cheroliv.opticfiber.domain.enumerations.ContractEnum
import com.cheroliv.opticfiber.domain.enumerations.TypeInterEnum
import com.cheroliv.opticfiber.inter.entity.InterEntity
import org.springframework.context.ApplicationContext
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean

import javax.persistence.EntityManager
import java.time.LocalDateTime

import static com.cheroliv.opticfiber.util.ApplicationUtils.parseStringDateToLocalDate
import static com.cheroliv.opticfiber.util.ApplicationUtils.parseStringHeureToLocalTime
import static com.cheroliv.opticfiber.util.ApplicationUtils.getSeparator
import static com.cheroliv.opticfiber.config.InterConstants.*

class TestUtils {

    static EntityManager getEntityManager(ApplicationContext applicationContext) {
        applicationContext
                .getBean(LocalContainerEntityManagerFactoryBean)
                .getNativeEntityManagerFactory()
                .createEntityManager()
    }

    static InterEntity mapToInter(Map<String, String> map) {
        new InterEntity(
                id: Long.parseLong(map[ID_INTER_JSON_FIELD_NAME]),
                nd: map[ND_INTER_JSON_FIELD_NAME],
                lastNameClient: map[LASTNAME_INTER_JSON_FIELD_NAME],
                firstNameClient: map[FIRSTNAME_INTER_JSON_FIELD_NAME],
                contract: ContractEnum.valueOfName(map[CONTRACT_INTER_JSON_FIELD_NAME]),
                typeInter: TypeInterEnum.valueOfName(map[TYPE_INTER_JSON_FIELD_NAME]),
                dateTimeInter: LocalDateTime.of(
                        parseStringDateToLocalDate(map[DATE_INTER_JSON_FIELD_NAME]),
                        parseStringHeureToLocalTime(map[INTER_HEURE_COLUMN_NAME])))
    }

    static Properties getApplicationProperties() {
        File applicationDotPopertiesFile =
                new File(new File('.').canonicalPath +
                        getSeparator() +
                        'src' + getSeparator() +
                        'test' + getSeparator() +
                        'resources' + getSeparator() +
                        'application.properties')

        assert applicationDotPopertiesFile,
                'Requested resource is not a file\n' +
                        'la resource demandé n\'' +
                        'est pas un fichier'
        Properties appProperties = new Properties()
        applicationDotPopertiesFile.withInputStream { InputStream it ->
            appProperties.load(it)
        }
        appProperties
    }


}
