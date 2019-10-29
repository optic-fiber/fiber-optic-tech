package com.cheroliv.core.repository

import com.cheroliv.core.domain.AuthorityDto
import com.cheroliv.core.domain.UserDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

import java.time.Instant

interface UserRepository {

    String USERS_BY_LOGIN_CACHE = "usersByLogin"

    String USERS_BY_EMAIL_CACHE = "usersByEmail"

    Optional<UserDto> findById(Long id)

    Optional<AuthorityDto> findAuthorityById(String authorityName)

    Optional<UserDto> findOneByActivationKey(String activationKey)

    Optional<UserDto> findOneByResetKey(String resetKey)

    Optional<UserDto> findOneByEmailIgnoreCase(String email)

    Optional<UserDto> findOneByLogin(String login)

    void delete(UserDto userDto)

    void flush()

    UserDto save(UserDto userDto)

    Page<UserDto> findAllByLoginNot(Pageable pageable, String login)

    Optional<UserDto> findOneWithAuthoritiesByLogin(String login)

    Optional<UserDto> findOneWithAuthoritiesById(Long id)

    List<AuthorityDto> findAllAuthorities()

    List<UserDto> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant instant)

    AuthorityDto saveAuthority(AuthorityDto authorityDto)
}