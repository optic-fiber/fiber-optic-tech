package com.cheroliv.opticfiber.planning.service

import com.cheroliv.opticfiber.planning.domain.PlanningDto
import com.cheroliv.opticfiber.planning.repository.PlanningRepository
import groovy.transform.TypeChecked
import org.springframework.stereotype.Service

@Service
@TypeChecked
class PlanningServiceImp implements PlanningService {

    final PlanningRepository repository

    PlanningServiceImp(PlanningRepository repository) {
        this.repository = repository
    }

    @Override
    PlanningDto save(PlanningDto planningDto) {
        repository.save(planningDto)
    }
}
