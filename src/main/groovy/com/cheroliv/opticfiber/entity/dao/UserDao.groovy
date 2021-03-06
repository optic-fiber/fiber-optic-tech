package com.cheroliv.opticfiber.entity.dao


import com.cheroliv.opticfiber.entity.UserEntity
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

import java.time.Instant

interface UserDao extends JpaRepository<UserEntity, Long> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin"

    String USERS_BY_EMAIL_CACHE = "usersByEmail"

    Optional<UserEntity> findOneByActivationKey(String activationKey)

    Optional<UserEntity> findOneByResetKey(String resetKey)

    List<UserEntity> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime)

    Optional<UserEntity> findOneByEmailIgnoreCase(String email)

    Optional<UserEntity> findOneByLogin(String login)

    @EntityGraph(attributePaths = "authorities")
    Optional<UserEntity> findOneWithAuthoritiesById(Long id)

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<UserEntity> findOneWithAuthoritiesByLogin(String login)

    @EntityGraph(attributePaths = "authorities")
    @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
    Optional<UserEntity> findOneWithAuthoritiesByEmail(String email)

    Page<UserEntity> findAllByLoginNot(Pageable pageable, String login)

}