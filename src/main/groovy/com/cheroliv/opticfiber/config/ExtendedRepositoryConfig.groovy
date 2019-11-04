package com.cheroliv.opticfiber.config

import com.cheroliv.core.entity.dao.ExtendedDaoImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
        repositoryBaseClass = ExtendedDaoImpl,
        basePackages = [
                'com.cheroliv.core.entity.dao',
                'com.cheroliv.core.config',
                'com.cheroliv.core.repository',
                'com.cheroliv.opticfiber.inter.entity.dao',
                'com.cheroliv.opticfiber.planning.entity.dao'])
class ExtendedRepositoryConfig {
}