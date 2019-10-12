package com.cheroliv.opticfiber


import com.cheroliv.opticfiber.config.InterConstants
import com.cheroliv.opticfiber.inter.domain.enumeration.ContractEnum
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import com.cheroliv.opticfiber.inter.entity.InterEntity
import org.springframework.context.ApplicationContext
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean

import javax.persistence.EntityManager
import java.time.LocalDateTime

class TestUtils {

    static EntityManager getEntityManager(ApplicationContext applicationContext) {
        applicationContext
                .getBean(LocalContainerEntityManagerFactoryBean)
                .getNativeEntityManagerFactory()
                .createEntityManager()
    }
    static InterEntity mapToInter(Map<String, String> map) {
        new InterEntity(
                id: Long.parseLong(map[InterConstants.ID_INTER_JSON_FIELD_NAME]),
                nd: map[InterConstants.ND_INTER_JSON_FIELD_NAME],
                lastNameClient: map[InterConstants.LASTNAME_INTER_JSON_FIELD_NAME],
                firstNameClient: map[InterConstants.FIRSTNAME_INTER_JSON_FIELD_NAME],
                contract: ContractEnum.valueOfName(map[InterConstants.CONTRACT_INTER_JSON_FIELD_NAME]),
                typeInter: TypeInterEnum.valueOfName(map[InterConstants.TYPE_INTER_JSON_FIELD_NAME]),
                dateTimeInter: LocalDateTime.of(
                        parseStringDateToLocalDate(map[InterConstants.DATE_INTER_JSON_FIELD_NAME]),
                        parseStringHeureToLocalTime(map[InterConstants.INTER_HEURE_COLUMN_NAME])))
    }


}
