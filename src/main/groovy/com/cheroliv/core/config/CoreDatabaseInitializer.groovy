package com.cheroliv.core.config

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Slf4j
@Component
class CoreDatabaseInitializer {
    @PostConstruct
    void initializeDatabase() {
        log.info(this.class.simpleName+'initializeDatabase()')
    }



        void createDefaultAuth() {
//        if (!authorityRepository.findById(AuthoritiesConstants.USER).present)
//            authorityRepository.save(new AuthorityEntity(name: AuthoritiesConstants.USER))
//        if (!authorityRepository.findById(AuthoritiesConstants.ADMIN).present)
//            authorityRepository.save(new AuthorityEntity(name: AuthoritiesConstants.ADMIN))
//        if (!authorityRepository.findById(AuthoritiesConstants.ANONYMOUS).present)
//            authorityRepository.save(new AuthorityEntity(name: AuthoritiesConstants.ANONYMOUS))
    }
//
    void createDefaultUsers() {
//        Optional<UserEntity> optionalAdminUser = userService.getUserWithAuthoritiesByLogin "admin"
//        if (!optionalAdminUser.present) {
//            userService.registerUser(new UserDTO(new UserEntity(
//                    login: "admin",
//                    firstName: "admin",
//                    lastName: "admin",
//                    email: "admin@localhost")),
//                    "admin")
//            UserEntity user = userRepository.findOneByLogin("admin")?.get()
//            user.activated = true
//            user.authorities = [
//                    authorityRepository.findById(AuthoritiesConstants.USER)?.get(),
//                    authorityRepository.findById(AuthoritiesConstants.ADMIN)?.get()
//            ] as Set
//            userRepository.save(user)
//        }
//        Optional<UserEntity> optionalUser = userService.getUserWithAuthoritiesByLogin("user")
//        if (!optionalUser.present) {
//            userService.registerUser(new UserDTO(new UserEntity(
//                    login: "user",
//                    firstName: "user",
//                    lastName: "user",
//                    email: "user@localhost")),
//                    "user")
//            UserEntity user = userRepository.findOneByLogin("user")?.get()
//            user.activated = true
//            user.authorities = [
//                    authorityRepository.findById(AuthoritiesConstants.USER)?.get()
//            ] as Set
//            userRepository.save(user)
//            createDefaultPlanning()
//        }
//        Optional<UserEntity> optionalSystemUser = userService.getUserWithAuthoritiesByLogin("system")
//        if (!optionalSystemUser.present) {
//            userService.registerUser(new UserDTO(new UserEntity(
//                    login: "system",
//                    firstName: "system",
//                    lastName: "system",
//                    email: "system@localhost")),
//                    "system")
//            UserEntity user = userRepository.findOneByLogin("system")?.get()
//            user.activated = true
//            user.authorities = [authorityRepository.findById(AuthoritiesConstants.ADMIN)?.get(),
//                                authorityRepository.findById(AuthoritiesConstants.USER)?.get()] as Set
//            userRepository.save(user)
//        }
//        Optional<UserEntity> optionalAnonymousUser = userService.getUserWithAuthoritiesByLogin("anonymoususer")
//        if (!optionalAnonymousUser.present) {
//            userService.registerUser(new UserDTO(new UserEntity(
//                    login: "anonymoususer",
//                    firstName: "anonymoususer",
//                    lastName: "anonymoususer",
//                    email: "anonymoususer@localhost")),
//                    "anonymoususer")
//            UserEntity user = userRepository.findOneByLogin("anonymoususer")?.get()
//            user.activated = true
//            user.authorities = [] as Set
//            userRepository.save(user)
//        }
    }



}
