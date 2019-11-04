package com.cheroliv.opticfiber.entity.dao

import com.cheroliv.core.entity.AuthorityEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityDao extends JpaRepository<AuthorityEntity, String> {
}
