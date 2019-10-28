package com.cheroliv.core.repository

import com.cheroliv.core.domain.AuthorityDto
import com.cheroliv.core.domain.UserDto

interface UserRepository {
    Optional<AuthorityDto> findById(String authorityName)

    Optional<UserDto> findOneByActivationKey(String key)
}