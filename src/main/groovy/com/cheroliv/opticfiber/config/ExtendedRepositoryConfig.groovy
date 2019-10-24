package com.cheroliv.opticfiber.config

import com.cheroliv.core.entity.dao.ExtendedRepositoryImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
        basePackages = [
                'com.cheroliv.core.entity.dao',
                'com.cheroliv.opticfiber.inter.entity.dao',
                'com.cheroliv.opticfiber.planning.entity.dao'
],
        repositoryBaseClass = ExtendedRepositoryImpl)
class ExtendedRepositoryConfig {
}