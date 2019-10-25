package com.cheroliv.opticfiber.inter.entity

import com.cheroliv.opticfiber.inter.domain.enumeration.ContractEnum
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum

import java.time.LocalDateTime

interface InterEntityGeneric<ID> {
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

    void setFirstNameClient(String fn)

    String getLastNameClient()

    void setLastNameClient(String ln)

}