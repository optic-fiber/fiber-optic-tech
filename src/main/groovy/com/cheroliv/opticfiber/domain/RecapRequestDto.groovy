package com.cheroliv.opticfiber.domain

import groovy.transform.ToString

import java.time.LocalDateTime

@ToString
class RecapRequestDto {
    LocalDateTime startDate
    LocalDateTime endDate
}
