package com.cheroliv.opticfiber

import com.cheroliv.opticfiber.domain.UserDto
import com.cheroliv.core.entity.AuthorityEntity
import com.cheroliv.core.entity.UserEntity
import com.cheroliv.core.config.AuthoritiesConstants
import com.cheroliv.opticfiber.domain.InterDto
import com.cheroliv.opticfiber.inter.domain.enumeration.ContractEnum
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import com.cheroliv.opticfiber.inter.entity.InterEntity
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

import static com.cheroliv.opticfiber.config.ApplicationConstants.DATE_PATTERN_FORMAT

@Slf4j
@Singleton
@CompileStatic
class TestData {

    static final InterDto firstInterDto = new InterDto(
            id: 1, nd: "0144639035",
            lastName: "Lalande", firstName: "Julien",
            dateTime: stringToLocalDateTimeSystemDefault(
                    "2018-10-29",
                    "10:00:00"),
            contract: "IQ", typeInter: "BAOC")

    static final InterDto lastInterDto = new InterDto(
            id: 109, nd: "0143485957",
            lastName: "Bouvier", firstName: "Steven",
            dateTime: stringToLocalDateTimeSystemDefault(
                    "2019-01-04",
                    "12:00:00"),
            contract: "IQ", typeInter: "BAAP")

    static final InterDto interDto = new InterDto(
            id: 104, nd: "0144820811",
            lastName: "Gustin", firstName: "Jean-Pierre",
            contract: "IQ", typeInter: "BAOC",
            dateTime: stringToLocalDateTimeSystemDefault(
                    "2019-01-02",
                    "13:00:00"))

    static final InterDto prevInterDto = new InterDto(
            id: 103, nd: "0142069836",
            lastName: "Maugee", firstName: "Eric",
            dateTime: stringToLocalDateTimeSystemDefault(
                    "2018-12-31",
                    "12:00:00"),
            contract: "IQ", typeInter: "BAAP")

    static final InterDto nextInterDto = new InterDto(
            id: 105, nd: "0143486423",
            lastName: "QUANTUM",
            dateTime: stringToLocalDateTimeSystemDefault(
                    "2019-01-02",
                    "10:00:00"),
            contract: "LM", typeInter: "BAAP")

    static final Integer newInterDtoId = 106

    static final InterDto newInterDto =
            new InterDto(
                    nd: "0102030405",
                    lastName: 'Doe',
                    firstName: 'John',
                    dateTime: LocalDateTime.now(),
                    contract: ContractEnum.LM.name(),
                    typeInter: TypeInterEnum.BAFA.name())

    static final InterDto expectedPersistedInterDto =
            new InterDto(
                    id: newInterDtoId,
                    nd: newInterDto.nd,
                    dateTime: newInterDto.dateTime,
                    firstName: newInterDto.firstName,
                    lastName: newInterDto.lastName,
                    typeInter: newInterDto.typeInter,
                    contract: newInterDto.contract)

    static final List<InterDto> interDtos = [
            firstInterDto, prevInterDto, interDto,
            nextInterDto, lastInterDto]
    static final List<InterEntity> interEntities = [
            firstInter, prevInter, inter,
            nextInter, lastInter]


    static final String jsonNewtInterDto =
            '{"nd":"0102030405",' +
                    '"firstName":"John","lastName":"Doe",' +
                    '"contract":"LM","typeInter":"BAFA",' +
                    '"dateTime":"2019-09-30 17:47:44"}'

    static final String jsonFirstInterDto = '{"id":1,' +
            '"nd":"0144639035","firstName":"Julien",' +
            '"lastName":"Lalande","contract":"IQ",' +
            '"typeInter":"BAOC","dateTime":' +
            '"2018-10-29 10:00:00"}'

    static final String jsonFirstInterDtoWithoutId =
            '{"nd":"0144639035","firstName":"Julien",' +
                    '"lastName":"Lalande","contract":"IQ",' +
                    '"typeInter":"BAOC","dateTime":' +
                    '"2018-10-29 10:00:00"}'

    static final InterEntity firstInter = new InterEntity(
            id: firstInterDto.id,
            nd: firstInterDto.nd,
            firstNameClient: firstInterDto.firstName,
            lastNameClient: firstInterDto.lastName,
            dateTimeInter: firstInterDto.dateTime,
            contract: ContractEnum.valueOfName(
                    firstInterDto.contract),
            typeInter: TypeInterEnum.valueOfName(
                    firstInterDto.typeInter))

    static final InterEntity inter = new InterEntity(
            id: 104, nd: interDto.nd,
            lastNameClient: interDto.lastName,
            firstNameClient: interDto.firstName,
            contract: ContractEnum
                    .valueOfName(interDto.contract),
            typeInter: TypeInterEnum
                    .valueOfName(interDto.typeInter),
            dateTimeInter: interDto.dateTime)

    static final InterEntity prevInter = new InterEntity(
            id: prevInterDto.id,
            nd: prevInterDto.nd,
            firstNameClient: prevInterDto.firstName,
            lastNameClient: prevInterDto.lastName,
            contract: ContractEnum.valueOfName(
                    prevInterDto.contract),
            typeInter: TypeInterEnum.valueOfName(
                    prevInterDto.typeInter),
            dateTimeInter: prevInterDto.dateTime)

    static final InterEntity lastInter = new InterEntity(
            id: lastInterDto.id,
            nd: lastInterDto.nd,
            firstNameClient: lastInterDto.firstName,
            lastNameClient: lastInterDto.lastName,
            contract: ContractEnum.valueOfName(
                    lastInterDto.contract),
            typeInter: TypeInterEnum.valueOfName(
                    lastInterDto.typeInter),
            dateTimeInter: lastInterDto.dateTime)

    static final InterEntity nextInter = new InterEntity(
            id: nextInterDto.id,
            nd: nextInterDto.nd,
            firstNameClient: nextInterDto.firstName,
            lastNameClient: nextInterDto.lastName,
            contract: ContractEnum.valueOfName(
                    nextInterDto.contract),
            typeInter: TypeInterEnum.valueOfName(
                    nextInterDto.typeInter),
            dateTimeInter: nextInterDto.dateTime)

    static final List nomFeuilles = [
            'octobre 2018',
            'décembre 2018',
            'janvier 2019']

    static final List<LocalDateTime> dates = interDtos.collect {
        it.dateTime
    }

    static LocalDateTime stringToLocalDateTimeSystemDefault(
            String date, String time) {
        LocalDateTime.of(
                LocalDate.parse(date, DateTimeFormatter
                        .ofPattern(DATE_PATTERN_FORMAT)),
                LocalTime.parse(time, DateTimeFormatter
                        .ofPattern("HH:mm:ss")))
    }

    static final AuthorityEntity userAuth = new AuthorityEntity(name: AuthoritiesConstants.USER)
    static final AuthorityEntity adminAuth = new AuthorityEntity(name: AuthoritiesConstants.ADMIN)
    static final AuthorityEntity anonymousAuth = new AuthorityEntity(name: AuthoritiesConstants.ANONYMOUS)


    static final UserDto userDto = new UserEntity(
            login: "user",
            firstName: "user",
            lastName: "user",
            email: "user@localhost").toDto()

    static final UserDto systemUserDto = new UserEntity(
            login: "system",
            firstName: "system",
            lastName: "system",
            email: "system@localhost").toDto()

    static final UserDto anonymousUserDto = new UserEntity(
            login: "anonymoususer",
            firstName: "anonymoususer",
            lastName: "anonymoususer",
            email: "anonymoususer@localhost").toDto()
}
