package com.cheroliv.opticfiber.planning.entity


import com.cheroliv.core.entity.UserEntity
import com.cheroliv.opticfiber.inter.entity.InterEntity
import com.cheroliv.opticfiber.domain.PlanningDto
import groovy.transform.ToString
import groovy.transform.TypeChecked

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import java.time.LocalDateTime

import static javax.persistence.GenerationType.SEQUENCE

@Entity
@ToString
@TypeChecked
@Table(name = "`planning`", indexes = [
        @Index(name = "`idx_planning_initial_tech`", columnList = "`initial_tech`"),
        @Index(name = "`idx_planning_open`", columnList = "`open`"),
        @Index(name = "`idx_planning_date_time_creation`", columnList = "`date_time_creation`"),
        @Index(name = "`idx_planning_last_name_tech`", columnList = "`last_name_tech`"),
        @Index(name = "`idx_planning_first_name_tech`", columnList = "`first_name_tech`")])
class PlanningEntity implements PlanningEntityGeneric<Long, InterEntity> {

    @Id
    @GeneratedValue(strategy = SEQUENCE,
            generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "`id`")
    Long id
    @NotNull
    @Size(min = 2)
    @Column(name = "`initial_tech`", nullable = false)
    String initialTech
    @NotNull
    @Column(name = "`open`", nullable = false)
    Boolean open
    @NotNull
    @Column(name = "`date_time_creation`")
    LocalDateTime dateTimeCreation
    @Size(max = 100)
    @Column(name = "`last_name_tech`", length = 100)
    String lastNameTech
    @Size(max = 100)
    @Column(name = "`first_name_tech`", length = 100)
    String firstNameTech
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "`user_id`", nullable = false)
    UserEntity user

    static PlanningDto fromEntity(PlanningEntity planningEntity) {
        planningEntity.toDto()
    }
static PlanningEntity fromDto(PlanningDto dto){
    new PlanningEntity(
        id: dto.id,
        initialTech: dto.initialTech,
        dateTimeCreation:dto.dateTimeCreation,
        open: dto.open,
        lastNameTech: dto.lastNameTech,
        firstNameTech: dto.firstNameTech,
        user: UserEntity.fromDto(dto.user)
    )
}
    PlanningDto toDto() {
        new PlanningDto(
            id: this.id,
            initialTech: this.initialTech,
            dateTimeCreation:this.dateTimeCreation,
            open: this.open,
            lastNameTech: this.lastNameTech,
            firstNameTech: this.firstNameTech,
            user: this.user.toDto()
        )
    }
//    List<InterEntity> interEntities


    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        PlanningEntity planning = (PlanningEntity) o

        if (dateTimeCreation != planning.dateTimeCreation) return false
        if (firstNameTech != planning.firstNameTech) return false
        if (lastNameTech != planning.lastNameTech) return false
        if (open != planning.open) return false
        if (initialTech != planning.initialTech) return false

        return true
    }

    int hashCode() {
        int result
        result = (initialTech != null ? initialTech.hashCode() : 0)
        result = 31 * result + (open != null ? open.hashCode() : 0)
        result = 31 * result + (dateTimeCreation != null ? dateTimeCreation.hashCode() : 0)
        result = 31 * result + (lastNameTech != null ? lastNameTech.hashCode() : 0)
        result = 31 * result + (firstNameTech != null ? firstNameTech.hashCode() : 0)
        return result
    }
}
