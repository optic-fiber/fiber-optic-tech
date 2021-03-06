package com.cheroliv.opticfiber.domain

import com.cheroliv.opticfiber.domain.enumerations.ContractEnum
import com.cheroliv.opticfiber.domain.enumerations.TypeInterEnum
import groovy.transform.CompileStatic
import groovy.transform.Immutable

import java.time.LocalDateTime

@CompileStatic
@Immutable(knownImmutables = ['optionalInter'])
class Intervention implements Serializable {
    Optional<InterDto> optionalInter
    String nd
    LocalDateTime dateTime
    String firstName
    String lastName
    ContractEnum contract
    TypeInterEnum typeInter
}
