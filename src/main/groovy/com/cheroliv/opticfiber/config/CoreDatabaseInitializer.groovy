package com.cheroliv.opticfiber.config

import com.cheroliv.opticfiber.domain.AuthorityDto
import com.cheroliv.opticfiber.repository.UserRepository
import groovy.util.logging.Slf4j
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import static com.cheroliv.opticfiber.config.ApplicationConstants.*

@Slf4j
@Component
class CoreDatabaseInitializer implements ApplicationContextAware {


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
        if (!userRepository.findAuthorityById(USER).present)
            userRepository.saveAuthority(new AuthorityDto(name:USER))
        if (!userRepository.findAuthorityById(ADMIN).present)
            userRepository.saveAuthority(new AuthorityDto(name:ADMIN))
        if (!userRepository.findAuthorityById(ANONYMOUS).present)
            userRepository.saveAuthority(new AuthorityDto(name: ANONYMOUS))
    }

    void createDefaultUsers() {
        log.info(this.class.simpleName + 'createDefaultUsers()')
//        UserService userService = applicationContext
//                .getBean(UserService)
////        Optional<UserEntity> optionalAdminUser = userService.getUserWithAuthoritiesByLogin "admin"
////        if (!optionalAdminUser.present) {
////            userService.registerUser(new UserDTO(new UserEntity(
////                    login: "admin",
////                    firstName: "admin",
////                    lastName: "admin",
////                    email: "admin@localhost")),
////                    "admin")
////            UserEntity user = userRepository.findOneByLogin("admin")?.get()
////            user.activated = true
////            user.authorities = [
////                    authorityRepository.findById(AuthoritiesConstants.USER)?.get(),
////                    authorityRepository.findById(AuthoritiesConstants.ADMIN)?.get()
////            ] as Set
////            userRepository.save(user)
////        }
////        Optional<UserEntity> optionalUser = userService.getUserWithAuthoritiesByLogin("user")
////        if (!optionalUser.present) {
////            userService.registerUser(new UserDTO(new UserEntity(
////                    login: "user",
////                    firstName: "user",
////                    lastName: "user",
////                    email: "user@localhost")),
////                    "user")
////            UserEntity user = userRepository.findOneByLogin("user")?.get()
////            user.activated = true
////            user.authorities = [
////                    authorityRepository.findById(AuthoritiesConstants.USER)?.get()
////            ] as Set
////            userRepository.save(user)
////            createDefaultPlanning()
////        }
////        Optional<UserEntity> optionalSystemUser = userService.getUserWithAuthoritiesByLogin("system")
////        if (!optionalSystemUser.present) {
////            userService.registerUser(new UserDTO(new UserEntity(
////                    login: "system",
////                    firstName: "system",
////                    lastName: "system",
////                    email: "system@localhost")),
////                    "system")
////            UserEntity user = userRepository.findOneByLogin("system")?.get()
////            user.activated = true
////            user.authorities = [authorityRepository.findById(AuthoritiesConstants.ADMIN)?.get(),
////                                authorityRepository.findById(AuthoritiesConstants.USER)?.get()] as Set
////            userRepository.save(user)
////        }
////        Optional<UserEntity> optionalAnonymousUser = userService.getUserWithAuthoritiesByLogin("anonymoususer")
////        if (!optionalAnonymousUser.present) {
////            userService.registerUser(new UserDTO(new UserEntity(
////                    login: "anonymoususer",
////                    firstName: "anonymoususer",
////                    lastName: "anonymoususer",
////                    email: "anonymoususer@localhost")),
////                    "anonymoususer")
////            UserEntity user = userRepository.findOneByLogin("anonymoususer")?.get()
////            user.activated = true
////            user.authorities = [] as Set
////            userRepository.save(user)
////        }
    }


}
