package com.cheroliv.opticfiber.entity.dao

import com.cheroliv.opticfiber.domain.enumerations.TypeInterEnum
import com.cheroliv.opticfiber.inter.entity.InterEntityGeneric

interface InterDaoGeneric<ENTITY extends InterEntityGeneric> {
    Optional<ENTITY> find(
            String nd,
            TypeInterEnum type)

}