package com.cheroliv.core.repository

import com.cheroliv.core.domain.AuthorityDto
import com.cheroliv.core.entity.AuthorityEntity
import com.cheroliv.core.entity.dao.AuthorityDao
import com.cheroliv.core.entity.dao.UserDao
import org.springframework.stereotype.Service

@Service
class UserRepositoryImp implements UserRepository {
    final UserDao userDao
    final AuthorityDao authorityDao

    UserRepositoryImp(
            UserDao userDao,
            AuthorityDao authorityDao) {
        this.userDao = userDao
        this.authorityDao = authorityDao
    }

    @Override
    Optional<AuthorityDto> findById(String authorityName) {
        Optional<AuthorityEntity> result =
                authorityDao.findById(authorityName)
        if (result.empty) Optional.empty()
        else Optional.of(AuthorityEntity.fromEntity(result.get()))
    }
}
