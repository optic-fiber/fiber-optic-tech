package com.cheroliv.opticfiber.config

//import com.cheroliv.core.repository.UserRepository
//import com.cheroliv.opticfiber.planning.domain.PlanningDto
//import com.cheroliv.opticfiber.planning.service.PlanningService
//import groovy.transform.TypeChecked
//import groovy.util.logging.Slf4j
//import org.springframework.boot.autoconfigure.domain.EntityScan
//import org.springframework.context.ApplicationContext
//import org.springframework.context.ApplicationContextAware
//import org.springframework.context.annotation.Configuration
//import org.springframework.transaction.annotation.Transactional
//
//import javax.annotation.PostConstruct
//import java.time.LocalDateTime

//@Slf4j
//@TypeChecked
//@Transactional
//@Configuration
//@EntityScan(['com.cheroliv.core.entity',
//        'com.cheroliv.opticfiber.inter.entity',
//        'com.cheroliv.opticfiber.planning.entity'])
class DatabaseInitializer {}/*implements ApplicationContextAware {

    ApplicationContext applicationContext
//    final UserService userService
//    final AuthorityDao authorityRepository
    final UserRepository userRepository
    final PlanningService planningService
//
//
    DatabaseInitializer(
//            UserService userService,
//                        AuthorityDao authorityRepository,
                        UserRepository userRepository,
                        PlanningService planningService
    ) {
//        this.userService = userService
//        this.authorityRepository = authorityRepository
        this.userRepository = userRepository
        this.planningService = planningService
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
        def planningService = applicationContext.getBean(PlanningService)
        def userRepository = applicationContext.getBean(UserRepository)
        planningService.save(new PlanningDto(
                user: userRepository.findOneByLogin("user")?.get(),
                dateTimeCreation: LocalDateTime.now(),
                initialTech: "UU",
                open: Boolean.TRUE,
                firstNameTech: "user",
                lastNameTech: "user"))
    }

    void loadDefaultPlanning() {
        log.info(this.class.simpleName + 'loadDefaultPlanning()')
    }
}*/
