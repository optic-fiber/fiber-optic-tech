package com.cheroliv.core.service

import com.cheroliv.opticfiber.domain.AuthorityDto
import com.cheroliv.opticfiber.domain.UserDto
import com.cheroliv.core.entity.UserEntityGeneric
import com.cheroliv.core.repository.UserRepository
import com.cheroliv.core.security.SecurityUtils
import com.cheroliv.core.service.exceptions.EmailAlreadyUsedException
import com.cheroliv.core.service.exceptions.InvalidPasswordException
import com.cheroliv.core.service.exceptions.UsernameAlreadyUsedException
import groovy.util.logging.Slf4j
import org.springframework.cache.CacheManager
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors

import static com.cheroliv.core.config.AuthoritiesConstants.USER
import static com.cheroliv.opticfiber.util.RandomUtil.*

@Slf4j
@Service
class UserServiceImp implements UserService {

    final UserRepository userRepository
    final CacheManager cacheManager
    final PasswordEncoder passwordEncoder

    UserServiceImp(
            UserRepository userRepository,
            CacheManager cacheManager,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository
        this.cacheManager = cacheManager
        this.passwordEncoder = passwordEncoder
    }

    private void clearUserCaches(UserDto user) {
        Objects.requireNonNull(cacheManager
                .getCache(UserRepository.USERS_BY_LOGIN_CACHE))
                .evict(user.getLogin())
        Objects.requireNonNull(cacheManager
                .getCache(UserRepository.USERS_BY_EMAIL_CACHE))
                .evict(user.getEmail())
    }

    @Override
    Optional<UserDto> activateRegistration(String activationKey) {
        log.debug("Activating user for activation key {}", activationKey)
        userRepository.findOneByActivationKey(activationKey)
                .map({ user ->
                    // activate given user for the registration key.
                    user.setActivated(true)
                    user.setActivationKey(null)
                    this.clearUserCaches(user)
                    log.debug("Activated user: {}", user)
                    user
                })
    }

    @Override
    Optional<UserDto> completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key)
        userRepository.findOneByResetKey(key)
                .filter({ user ->
                    user.resetDate.isAfter(
                            Instant.now().minusSeconds(86400))
                })
                .map({ user ->
                    user.setPassword(passwordEncoder.encode(newPassword))
                    user.setResetKey(null)
                    user.setResetDate(null)
                    this.clearUserCaches(user)
                    user
                })

    }

    @Override
    Optional<UserDto> requestPasswordReset(String mail) {
        userRepository.findOneByEmailIgnoreCase(mail)
                .filter({ user -> user.getActivated() })
                .map({ user ->
                    user.setResetKey(generateResetKey())
                    user.setResetDate(Instant.now())
                    this.clearUserCaches(user)
                    user
                })
    }

    private boolean removeNonActivatedUser(UserDto existingUser) {
        if (existingUser.activated) false
        else {
            userRepository.delete(existingUser)
            userRepository.flush()
            this.clearUserCaches(existingUser)
            true
        }
    }

    UserDto registerUser(UserDto userDto, String password) {
        userRepository.findOneByLogin(
                userDto.getLogin().toLowerCase())
                .ifPresent({ existingUser ->
                    if (!removeNonActivatedUser(existingUser))
                        throw new UsernameAlreadyUsedException()
                })
        userRepository.findOneByEmailIgnoreCase(
                userDto.getEmail())
                .ifPresent({ existingUser ->
                    if (!removeNonActivatedUser(existingUser))
                        throw new EmailAlreadyUsedException()
                })
        userDto.setAuthorities([USER] as Set<String>)
        userDto.setPassword(passwordEncoder.encode(password))
        userDto.setLogin(userDto.getLogin().toLowerCase())
        userDto.setEmail(userDto.getEmail().toLowerCase())
        userDto.setActivated(false)
        userDto.setActivationKey(generateActivationKey())
        userDto = userRepository.save(userDto)
        this.clearUserCaches(userDto)
        log.debug("Created Information for User: {}", userDto)
        userDto
    }


    @Override
    UserDto createUser(UserDto userDto) {
        userDto.setEmail(userDto.getEmail().toLowerCase())
        if (userDto.getLangKey() == null) // default language
            userDto.setLangKey(UserEntityGeneric.DEFAULT_LANGUAGE)
        userDto.setPassword(passwordEncoder.encode(generatePassword()))
        userDto.setResetKey(generateResetKey())
        userDto.setResetDate(Instant.now())
        userDto.setActivated(true)
        userDto = userRepository.save(userDto)
        this.clearUserCaches(userDto)
        log.debug("Created Information for User: {}", userDto)
        userDto

    }

    @Override
    void updateUser(String firstName,
                    String lastName,
                    String email,
                    String langKey,
                    String imageUrl) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap({ login ->
                    userRepository.findOneByLogin(login)
                })
                .ifPresent({ user ->
                    user.setFirstName(firstName)
                    user.setLastName(lastName)
                    user.setEmail(email.toLowerCase())
                    user.setLangKey(langKey)
                    user.setImageUrl(imageUrl)
                    this.clearUserCaches(user)
                    log.debug("Changed Information for User: {}", user)
                })

    }

    @Override
    Optional<UserDto> updateUser(UserDto userDto) {
        Optional<UserDto> optionalUpdateUserDto = userRepository.findById(userDto.id)
        if (optionalUpdateUserDto.isPresent()) {
            UserDto updateUserDto = optionalUpdateUserDto.get()
            this.clearUserCaches(updateUserDto)
            updateUserDto.setLogin(userDto.getLogin().toLowerCase())
            updateUserDto.setFirstName(userDto.getFirstName())
            updateUserDto.setLastName(userDto.getLastName())
            updateUserDto.setEmail(userDto.getEmail().toLowerCase())
            updateUserDto.setImageUrl(userDto.getImageUrl())
            updateUserDto.setActivated(userDto.isActivated())
            updateUserDto.setLangKey(userDto.getLangKey())
            updateUserDto.authorities.clear()
            updateUserDto.authorities = []
            userDto.authorities.each {
                String name ->
                    Optional<AuthorityDto> result =
                            userRepository.findAuthorityById(name)
                    if (result.isPresent())
                        updateUserDto.authorities.add(result.get().name)
            }
            this.clearUserCaches(updateUserDto)
            log.debug("Changed Information for User: {}", updateUserDto)
            Optional.of(updateUserDto)
        } else Optional.empty()
    }

    @Override
    void deleteUser(String login) {
        userRepository.findOneByLogin(login)
                .ifPresent({ user ->
                    userRepository.delete(user)
                    this.clearUserCaches(user)
                    log.debug("Deleted User: {}", user)
                })
    }

    @Override
    void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap({ login -> userRepository.findOneByLogin(login) })
                .ifPresent({ user ->
                    String currentEncryptedPassword = user.getPassword()
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException()
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword)
                    user.setPassword(encryptedPassword)
                    this.clearUserCaches(user)
                    log.debug("Changed password for User: {}", user)
                })
    }

    @Override
    @Transactional(readOnly = true)
    Page<UserDto> getAllManagedUsers(Pageable pageable) {
        userRepository.findAllByLoginNot(
                pageable, UserEntityGeneric.ANONYMOUS_USER)
    }

    @Override
    Optional<UserDto> getUserWithAuthoritiesByLogin(String login) {
        userRepository.findOneWithAuthoritiesByLogin(login)
    }

    @Override
    Optional<UserDto> getUserWithAuthorities(Long id) {
        userRepository.findOneWithAuthoritiesById(id)
    }

    @Override
    Optional<UserDto> getUserWithAuthorities() {
        return SecurityUtils.getCurrentUserLogin()
                .flatMap({ login ->
                    userRepository.findOneWithAuthoritiesByLogin(login)
                })
    }

    @Override
    @Scheduled(cron = "0 0 1 * * ?")
    void removeNotActivatedUsers() {
        userRepository
                .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(
                        Instant.now().minus(3, ChronoUnit.DAYS))
                .forEach({ user ->
                    log.debug("Deleting not activated user {}", user.getLogin())
                    userRepository.delete(user)
                    this.clearUserCaches(user)
                })
    }

    @Override
    List<String> getAuthorities() {
        return userRepository
                .findAllAuthorities()
                .stream()
                .map({ authority -> authority.getName() })
                .collect(Collectors.toList())
    }
}