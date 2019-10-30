package com.cheroliv.opticfiber.config

import com.cheroliv.core.entity.dao.ExtendedRepositoryImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
        repositoryBaseClass = ExtendedRepositoryImpl,
        basePackages = [



                'com.cheroliv.core.entity.dao',
                'com.cheroliv.core.config',
                'com.cheroliv.core.repository',



                'com.cheroliv.opticfiber.config',
                'com.cheroliv.opticfiber.inter.entity.dao',

                'com.cheroliv.opticfiber.planning.entity.dao',
                'com.cheroliv.opticfiber.planning.repository'



        ])
class ExtendedRepositoryConfig {
}