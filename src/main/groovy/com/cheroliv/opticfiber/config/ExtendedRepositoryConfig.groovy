package com.cheroliv.opticfiber.config

import com.cheroliv.core.entity.dao.ExtendedRepositoryImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
        basePackages = [


                'com.cheroliv.opticfiber',

                'com.cheroliv.core'



        ],
        repositoryBaseClass = ExtendedRepositoryImpl)
class ExtendedRepositoryConfig {
}