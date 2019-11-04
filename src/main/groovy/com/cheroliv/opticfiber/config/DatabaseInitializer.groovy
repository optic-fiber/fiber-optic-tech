package com.cheroliv.opticfiber.config


import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

import javax.annotation.PostConstruct

@Slf4j
@TypeChecked
@Transactional
@Configuration
@EntityScan(['com.cheroliv.opticfiber.entity'])
class DatabaseInitializer implements ApplicationContextAware {

    ApplicationContext applicationContext
//    final UserService userService
//    final AuthorityDao authorityRepository
//    final UserRepository userRepository
//    final PlanningRepository planningRepository
//
//
    DatabaseInitializer(
//            UserService userService,
//                        AuthorityDao authorityRepository,
//                        UserRepository userRepository,
//                        PlanningRepository planningRepository
    ) {
//        this.userService = userService
//        this.authorityRepository = authorityRepository
//        this.userRepository = userRepository
//        this.planningRepository = planningRepository
    }
//
    @PostConstruct
    void initializeDatabase() {
//        super.initializeDatabase()
        log.info(this.class.simpleName + 'initializeDatabase()')
        createDefaultPlanning()
    }

    void createDefaultPlanning() {
        log.info(this.class.simpleName + 'createDefaultPlanning()')
//        planningRepository.save new PlanningEntity(
//                user: userRepository.findOneByLogin("user")?.get(),
//                dateTimeCreation: LocalDateTime.now(),
//                initialTech: "UU",
//                open: Boolean.TRUE,
//                firstNameTech: "user",
//                lastNameTech: "user")
    }

    void loadDefaultPlanning() {
        log.info(this.class.simpleName + 'loadDefaultPlanning()')
    }
}
