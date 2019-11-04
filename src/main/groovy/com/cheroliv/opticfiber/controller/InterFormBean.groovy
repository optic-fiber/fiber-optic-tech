package com.cheroliv.opticfiber.controller

import com.cheroliv.opticfiber.domain.InterDto

import java.time.LocalDate
import java.time.LocalTime

class InterFormBean implements Serializable {
    LocalDate date
    LocalTime time
    InterDto inter
}
