package com.cheroliv.opticfiber


import com.cheroliv.opticfiber.inter.domain.enumeration.ContractEnum
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import com.cheroliv.opticfiber.inter.entity.InterEntity
import org.springframework.context.ApplicationContext
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean

import javax.persistence.EntityManager
import java.time.LocalDateTime

import static com.cheroliv.opticfiber.config.InterConstants.*
import static com.cheroliv.opticfiber.inter.domain.InterUtils.parseStringDateToLocalDate
import static com.cheroliv.opticfiber.inter.domain.InterUtils.parseStringHeureToLocalTime

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


}
