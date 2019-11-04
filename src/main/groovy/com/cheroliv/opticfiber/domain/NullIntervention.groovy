package com.cheroliv.opticfiber.domain

import com.cheroliv.opticfiber.inter.domain.enumeration.ContractEnum
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum

import java.time.LocalDateTime

class NullIntervention {
    Optional<InterDto> getOptionalInter() {
        Optional.empty()
    }

    String getNd() { null }

    TypeInterEnum getTypeInter() { null }

    ContractEnum getContract() { null }

    LocalDateTime getDateTimeInter() { null }

    String getFirstNameClient() { '' }

    String getLastNameClient() { '' }
}
