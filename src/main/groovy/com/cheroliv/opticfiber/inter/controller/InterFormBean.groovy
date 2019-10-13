package com.cheroliv.opticfiber.inter.controller

import com.cheroliv.opticfiber.inter.domain.InterDto

import java.time.LocalDate
import java.time.LocalTime

class InterFormBean implements Serializable {
    LocalDate date
    LocalTime time
    InterDto inter
}
