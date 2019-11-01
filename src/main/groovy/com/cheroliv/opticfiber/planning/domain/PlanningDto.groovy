package com.cheroliv.opticfiber.planning.domain

import com.cheroliv.core.domain.UserDto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import java.time.LocalDateTime

class PlanningDto {
    Long id
    @NotNull
    @Size(min = 2)
    String initialTech
    @NotNull
    Boolean open
    @NotNull
    LocalDateTime dateTimeCreation
    @Size(max = 100)
    String lastNameTech
    @Size(max = 100)
    String firstNameTech
    @NotNull
    UserDto user
//    List<InterEntity> interEntities
}
