package com.cheroliv.opticfiber.planning.entity.dao

import com.cheroliv.opticfiber.planning.entity.PlanningEntity
import groovy.transform.TypeChecked
import org.springframework.data.repository.PagingAndSortingRepository

@TypeChecked
interface PlanningDao extends PagingAndSortingRepository<PlanningEntity, Long> {
//    @Query("from PlanningEntity p where p.user.login=:login")
//    Slice<PlanningEntity> findByUserLogin(@Param("login") String login, Pageable pageable)
}
