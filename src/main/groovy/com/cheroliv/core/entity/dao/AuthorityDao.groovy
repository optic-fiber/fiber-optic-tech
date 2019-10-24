package com.cheroliv.core.entity.dao

import com.cheroliv.opticfiber.entity.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityDao extends JpaRepository<AuthorityEntity, String> {
}
