package com.cheroliv.opticfiber.inter.entity

import com.cheroliv.opticfiber.domain.enumerations.ContractEnum
import com.cheroliv.opticfiber.domain.enumerations.TypeInterEnum
import com.cheroliv.opticfiber.planning.entity.PlanningEntityGeneric

import java.time.LocalDateTime

interface InterEntityGeneric<ID, PLANNING extends PlanningEntityGeneric> extends Serializable {
    static final long serialVersionUID = 1L

    ID getId()

    void setId(ID id)

    String getNd()

    void setNd(String nd)

    TypeInterEnum getTypeInter()

    void setTypeInter(TypeInterEnum typeInterEnum)

    ContractEnum getContract()

    void setContract(ContractEnum contract)

    LocalDateTime getDateTimeInter()

    void setDateTimeInter(LocalDateTime dateTime)

    String getFirstNameClient()

    void setFirstNameClient(String firstNameClient)

    String getLastNameClient()

    void setLastNameClient(String lastNameClient)
}