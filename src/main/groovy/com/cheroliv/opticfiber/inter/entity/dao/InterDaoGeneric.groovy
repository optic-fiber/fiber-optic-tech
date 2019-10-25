package com.cheroliv.opticfiber.inter.entity.dao

import com.cheroliv.opticfiber.inter.domain.enumeration.TypeInterEnum
import com.cheroliv.opticfiber.inter.entity.InterEntityGeneric

interface InterDaoGeneric<ENTITY extends InterEntityGeneric> {
    Optional<ENTITY> find(
            String nd,
            TypeInterEnum type)

}