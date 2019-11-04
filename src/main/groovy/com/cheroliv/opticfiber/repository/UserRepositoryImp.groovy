package com.cheroliv.opticfiber.repository


import com.cheroliv.opticfiber.domain.AuthorityDto
import com.cheroliv.opticfiber.domain.UserDto
import com.cheroliv.opticfiber.entity.AuthorityEntity
import com.cheroliv.opticfiber.entity.UserEntity
import com.cheroliv.opticfiber.entity.dao.AuthorityDao
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.cheroliv.opticfiber.entity.dao.UserDao

import java.time.Instant

@Slf4j
@Service
@TypeChecked
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
    Optional<UserDto> findById(Long id) {
        Optional<UserEntity> result =
                userDao.findById(id)
        if (result.empty) Optional.empty()
        else Optional.of(UserEntity.fromEntity(result.get()))
    }


    @Override
    Optional<AuthorityDto> findAuthorityById(String authorityName) {
        Optional<AuthorityEntity> result =
                authorityDao.findById(authorityName)
        if (result.empty) Optional.empty()
        else Optional.of(AuthorityEntity.fromEntity(result.get()))
    }

    @Override
    Optional<UserDto> findOneByActivationKey(String activationKey) {
        Optional<UserEntity> result =
                userDao.findOneByActivationKey(activationKey)
        if (result.empty) Optional.empty()
        else Optional.of(UserEntity.fromEntity(result.get()))
    }

    @Override
    Optional<UserDto> findOneByResetKey(String resetKey) {
        Optional<UserEntity> result =
                userDao.findOneByResetKey(resetKey)
        if (result.empty) Optional.empty()
        else Optional.of(UserEntity.fromEntity(result.get()))
    }

    @Override
    Optional<UserDto> findOneByEmailIgnoreCase(String email) {
        Optional<UserEntity> result =
                userDao.findOneByEmailIgnoreCase(email)
        if (result.empty) Optional.empty()
        else Optional.of(UserEntity.fromEntity(result.get()))
    }

    @Override
    Optional<UserDto> findOneByLogin(String login) {
        Optional<UserEntity> result =
                userDao.findOneByLogin(login)
        if (result.empty) Optional.empty()
        else Optional.of(UserEntity.fromEntity(result.get()))
    }

    @Override
    void delete(UserDto userDto) {
        userDao.deleteById(userDto.id)
    }

    @Override
    void flush() {
        userDao.flush()
    }

    @Override
    UserDto save(UserDto userDto) {
        UserEntity userEntity = UserEntity.fromDto(userDto)
        if (!userDto.authorities || !userDto.authorities.empty) {
            userEntity.authorities = userDto.authorities.collect {
                String name -> this.authorityDao.findById(name)
            } as Set<AuthorityEntity>
        }
        userDao.save(userEntity).toDto()
    }

    @Override
    Page<UserDto> findAllByLoginNot(Pageable pageable, String login) {
        userDao.findAllByLoginNot(pageable, login).map(
                { entity ->
                    UserEntity.fromEntity(entity)
                })
    }

    @Override
    Optional<UserDto> findOneWithAuthoritiesByLogin(String login) {
        Optional<UserEntity> result =
                userDao.findOneWithAuthoritiesByLogin(login)
        if (result.empty) Optional.empty()
        else Optional.of(UserEntity.fromEntity(result.get()))
    }

    @Override
    Optional<UserDto> findOneWithAuthoritiesById(Long id) {
        Optional<UserEntity> result =
                userDao.findOneWithAuthoritiesById(id)
        if (result.empty) Optional.empty()
        else Optional.of(UserEntity.fromEntity(result.get()))
    }

    @Override
    List<AuthorityDto> findAllAuthorities() {
        authorityDao.findAll().collect {
            AuthorityEntity authorityEntity ->
                AuthorityEntity.fromEntity(authorityEntity)
        }
    }

    @Override
    List<UserDto> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant instant) {
        userDao.findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(instant)
                .collect {
                    entity -> UserEntity.fromEntity(entity)
                }
    }

    @Override
    AuthorityDto saveAuthority(AuthorityDto authorityDto) {
        authorityDao.save(AuthorityEntity.fromDto(authorityDto)).toDto()
    }
}
