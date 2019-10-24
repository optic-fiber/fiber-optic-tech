package com.cheroliv.opticfiber.config

import com.cheroliv.opticfiber.dao.ExtendedRepositoryImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
        basePackages = [


                'com.cheroliv.opticfiber.dao',

                'com.cheroliv.opticfiber.inter.entity.dao'



        ],
        repositoryBaseClass = ExtendedRepositoryImpl)
class ExtendedRepositoryConfig {
}