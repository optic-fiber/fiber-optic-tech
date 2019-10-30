package com.cheroliv.core.config

//import com.cheroliv.core.domain.AuthorityDto
//import com.cheroliv.core.domain.UserDto
//import com.cheroliv.core.repository.UserRepository
//import com.cheroliv.core.service.UserService
//import groovy.util.logging.Slf4j
//import org.springframework.context.ApplicationContext
//import org.springframework.context.ApplicationContextAware
//import org.springframework.stereotype.Component
//
//import javax.annotation.PostConstruct
//
//@Slf4j
//@Component
class CoreDatabaseInitializer {}/*implements ApplicationContextAware {


    ApplicationContext applicationContext

    @PostConstruct
    void initializeDatabase() {
        log.info(this.class.simpleName + 'initializeDatabase()')
        this.createDefaultAuth()
        this.createDefaultUsers()
    }


    void createDefaultAuth() {
        log.info(this.class.simpleName + 'createDefaultAuth()')
        UserRepository userRepository = applicationContext.getBean(UserRepository)
        if (!userRepository.findAuthorityById(AuthoritiesConstants.USER).present)
            userRepository.saveAuthority(new AuthorityDto(name: AuthoritiesConstants.USER))
        if (!userRepository.findAuthorityById(AuthoritiesConstants.ADMIN).present)
            userRepository.saveAuthority(new AuthorityDto(name: AuthoritiesConstants.ADMIN))
        if (!userRepository.findAuthorityById(AuthoritiesConstants.ANONYMOUS).present)
            userRepository.saveAuthority(new AuthorityDto(name: AuthoritiesConstants.ANONYMOUS))
    }

    void createDefaultUsers() {
        log.info(this.class.simpleName + 'createDefaultUsers()')
        UserService userService = applicationContext
                .getBean(UserService)
        UserRepository userRepository = applicationContext
                .getBean(UserRepository)
        Optional<UserDto> optionalAdminUser = userService.getUserWithAuthoritiesByLogin(AuthoritiesConstants.ADMIN_LOGIN)
        if (optionalAdminUser.empty) {
            userService.registerUser(new UserDto(
                    login: "admin",
                    firstName: "admin",
                    lastName: "admin",
                    email: "admin@localhost"),
                    "admin")
            UserDto user = userRepository.findOneByLogin("admin")?.get()
            user.activated = true
            user.authorities = [
                    userRepository.findAuthorityById(AuthoritiesConstants.USER)?.get()?.name,
                    userRepository.findAuthorityById(AuthoritiesConstants.ADMIN)?.get()?.name
            ] as Set<String>
            userRepository.save(user)
        }
        Optional<UserDto> optionalUser = userService.getUserWithAuthoritiesByLogin("user")
        if (!optionalUser.present) {
            userService.registerUser(new UserDto(
                    login: "user",
                    firstName: "user",
                    lastName: "user",
                    email: "user@localhost"),
                    "user")
            UserDto user = userRepository.findOneByLogin("user")?.get()
            user.activated = true
            user.authorities = [
                    userRepository.findAuthorityById(AuthoritiesConstants.USER)?.get()?.name
            ] as Set<String>
            userRepository.save(user)
        }
        Optional<UserDto> optionalSystemUser = userService.getUserWithAuthoritiesByLogin("system")
        if (!optionalSystemUser.present) {
            userService.registerUser(new UserDto(
                    login: "system",
                    firstName: "system",
                    lastName: "system",
                    email: "system@localhost"),
                    "system")
            UserDto user = userRepository.findOneByLogin("system")?.get()
            user.activated = true
            user.authorities = [
                    userRepository.findAuthorityById(AuthoritiesConstants.ADMIN)?.get()?.name,
                    userRepository.findAuthorityById(AuthoritiesConstants.USER)?.get()?.name
            ] as Set<String>
            userRepository.save(user)
        }
        Optional<UserDto> optionalAnonymousUser = userService.getUserWithAuthoritiesByLogin("anonymoususer")
        if (!optionalAnonymousUser.present) {
            userService.registerUser(new UserDto(
                    login: "anonymoususer",
                    firstName: "anonymoususer",
                    lastName: "anonymoususer",
                    email: "anonymoususer@localhost"),
                    "anonymoususer")
            UserDto user = userRepository.findOneByLogin("anonymoususer")?.get()
            user.activated = true
            user.authorities = [] as Set
            userRepository.save(user)
        }
    }
}
*/