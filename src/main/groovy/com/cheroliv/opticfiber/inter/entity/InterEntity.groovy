package com.cheroliv.opticfiber.inter.entity


import com.cheroliv.opticfiber.inter.domain.InterDto
import com.cheroliv.opticfiber.inter.domain.enumeration.ContractEnum
import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import com.cheroliv.opticfiber.planning.entity.PlanningEntity
import groovy.transform.ToString
import groovy.transform.TypeChecked
import org.hibernate.annotations.DynamicUpdate

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import java.time.LocalDateTime

import static com.cheroliv.opticfiber.config.InterConstants.ND_NOTNULL_CSTRT_TPL_MSG
import static com.cheroliv.opticfiber.config.InterConstants.ND_SIZE_CSTRT_TPL_MSG
import static javax.persistence.EnumType.STRING
import static javax.persistence.GenerationType.SEQUENCE

@Entity
@ToString
@TypeChecked
@DynamicUpdate
@Table(name = "`inter`", indexes = [
        @Index(name = "`uniq_idx_inter_nd_type`", columnList = "`nd`,`type_inter`", unique = true),
        @Index(name = "`idx_inter_type`", columnList = "`type_inter`"),
        @Index(name = "`idx_inter_contract`", columnList = "`contract`"),
        @Index(name = "`idx_inter_date_time_inter`", columnList = "`date_time_inter`", unique = true),
        @Index(name = "`idx_inter_first_name_client`", columnList = "`first_name_client`"),
        @Index(name = "`idx_inter_last_name_client`", columnList = "`last_name_client`")])
class InterEntity implements InterEntityGeneric<Long, PlanningEntity> {
    @Id
    @GeneratedValue(strategy = SEQUENCE,
            generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "`id`")
    Long id
    @Column(name = "`nd`",
            length = 10)
    @NotNull(message = ND_NOTNULL_CSTRT_TPL_MSG)
    @Size(min = 10, max = 10,
            message = ND_SIZE_CSTRT_TPL_MSG)
    String nd
    @NotNull
    @Enumerated(STRING)
    @Column(name = "`type_inter`",
            nullable = false,
            length = 4)
    TypeInterEnum typeInter
    @NotNull
    @Enumerated(STRING)
    @Column(name = "`contract`",
            nullable = false,
            length = 16)
    ContractEnum contract
    @NotNull
    @Column(name = "`date_time_inter`")
    LocalDateTime dateTimeInter
    @Size(max = 100)
    @Column(name = "`first_name_client`",
            length = 100)
    String firstNameClient
    @Size(max = 100)
    @Column(name = "`last_name_client`",
            length = 100)
    String lastNameClient

    InterDto toDto() {
        new InterDto(
                id: id,
                nd: nd,
                dateTime: dateTimeInter,
                contract: contract.name(),
                typeInter: typeInter.name(),
                firstName: firstNameClient,
                lastName: lastNameClient)
    }

    static InterEntity fromDto(InterDto dto) {
        new InterEntity(
                id: dto.id,
                nd: dto.nd,
                dateTimeInter: dto.dateTime,
                contract: ContractEnum.valueOfName(dto.contract),
                typeInter: TypeInterEnum.valueOfName(dto.typeInter),
                firstNameClient: dto.firstName,
                lastNameClient: dto.lastName)
    }

    static InterDto fromEntity(InterEntity i) {
        new InterDto(id: i.id,
                nd: i.nd,
                firstName: i.firstNameClient,
                lastName: i.lastNameClient,
                contract: i.contract.name(),
                typeInter: i.typeInter.name(),
                dateTime: i.dateTimeInter)
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (this.class != o.class) return false

        InterEntity inter = o as InterEntity

        if (contract != inter.contract) return false
        if (dateTimeInter != inter.dateTimeInter) return false
        if (firstNameClient != inter.firstNameClient) return false
        if (lastNameClient != inter.lastNameClient) return false
        if (nd != inter.nd) return false
        if (typeInter != inter.typeInter) return false

        return true
    }

    int hashCode() {
        int result
        result = (nd != null ? nd.hashCode() : 0)
        result = 31 * result + (typeInter != null ? typeInter.hashCode() : 0)
        result = 31 * result + (contract != null ? contract.hashCode() : 0)
        result = 31 * result + (dateTimeInter != null ? dateTimeInter.hashCode() : 0)
        result = 31 * result + (firstNameClient != null ? firstNameClient.hashCode() : 0)
        result = 31 * result + (lastNameClient != null ? lastNameClient.hashCode() : 0)
        return result
    }

}