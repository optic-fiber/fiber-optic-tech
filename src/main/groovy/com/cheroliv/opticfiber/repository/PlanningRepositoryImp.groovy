package com.cheroliv.opticfiber.repository


import com.cheroliv.opticfiber.domain.PlanningDto
import com.cheroliv.opticfiber.entity.PlanningEntity
import com.cheroliv.opticfiber.entity.dao.PlanningDao
import groovy.transform.TypeChecked
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service

@Service
@TypeChecked
class PlanningRepositoryImp implements PlanningRepository {

    final PlanningDao dao

    PlanningRepositoryImp(PlanningDao dao) {
        this.dao = dao
    }

    Slice<PlanningDto> findByUserLogin(String login, Pageable pageable) {
        dao.findByUserLogin(login, pageable).map({
            entity ->
                PlanningEntity.fromEntity(entity)
        })
    }

    PlanningDto save(PlanningDto planningDto) {
        dao.save(PlanningEntity.fromDto(planningDto)).toDto()
    }

}


//static UserEntity fromDto(UserDto dto) {
//    new UserEntity(
//            id: dto.id,
//            login: dto.login,
//            password: dto.password,
//            firstName: dto.firstName,
//            lastName: dto.lastName,
//            email: dto.email,
//            activated: dto.activated,
//            langKey: dto.langKey,
//            imageUrl: dto.imageUrl,
//            activationKey: dto.activationKey,
//            resetKey: dto.resetKey,
//            resetDate: dto.resetDate,
//            createdDate: dto.createdDate,
//            authorities: dto.authorities.collect {
//                authority ->
//                    new AuthorityEntity(name: authority)
//            } as Set<AuthorityEntity>)
//}
