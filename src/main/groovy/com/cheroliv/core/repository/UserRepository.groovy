package com.cheroliv.core.repository

import com.cheroliv.core.domain.AuthorityDto

interface UserRepository {
    Optional<AuthorityDto> findById(String authorityName)
}