package com.cheroliv.opticfiber.config

import com.cheroliv.opticfiber.repository.ExtendedRepositoryImpl
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EnableJpaRepositories(
        basePackages = ["com.cheroliv.opticfiber.inter.entity.dao",
                "com.cheroliv.opticfiber.repository"],
        repositoryBaseClass = ExtendedRepositoryImpl)
class ExtendedRepositoryConfig {
}