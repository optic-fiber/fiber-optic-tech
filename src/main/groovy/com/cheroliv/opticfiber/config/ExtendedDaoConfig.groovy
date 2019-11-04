package com.cheroliv.opticfiber.config

import com.cheroliv.opticfiber.entity.dao.ExtendedDaoImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
        repositoryBaseClass = ExtendedDaoImpl,
        basePackages = [
                'com.cheroliv.core.config',
                'com.cheroliv.opticfiber.repository',
                'com.cheroliv.opticfiber.entity.dao',
                'com.cheroliv.opticfiber.inter.entity.dao',
                'com.cheroliv.opticfiber.planning.entity.dao'])
class ExtendedDaoConfig {
}