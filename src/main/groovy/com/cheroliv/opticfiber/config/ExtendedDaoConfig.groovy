package com.cheroliv.opticfiber.config

import com.cheroliv.opticfiber.entity.dao.ExtendedDaoImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
        repositoryBaseClass = ExtendedDaoImpl,
        basePackages = [
                'com.cheroliv.opticfiber.entity.dao'])
class ExtendedDaoConfig {
}