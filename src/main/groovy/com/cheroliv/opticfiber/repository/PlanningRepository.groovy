package com.cheroliv.opticfiber.repository

import com.cheroliv.opticfiber.domain.PlanningDto
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice

interface PlanningRepository {
    PlanningDto save(PlanningDto planningDto)
    Slice<PlanningDto> findByUserLogin(String login, Pageable pageable)
}