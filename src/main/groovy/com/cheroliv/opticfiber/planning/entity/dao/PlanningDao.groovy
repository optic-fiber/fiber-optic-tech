package com.cheroliv.opticfiber.planning.entity.dao

import com.cheroliv.opticfiber.planning.entity.PlanningEntity
import groovy.transform.TypeChecked
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param

@TypeChecked
interface PlanningDao extends PagingAndSortingRepository<PlanningEntity, Long> {
    @Query("from PlanningEntity p where p.user.login=:login")
    Slice<PlanningEntity> findByUserLogin(@Param("login") String login, Pageable pageable)
}
