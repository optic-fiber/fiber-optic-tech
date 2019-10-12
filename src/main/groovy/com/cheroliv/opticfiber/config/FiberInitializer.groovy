package com.cheroliv.opticfiber.config

import com.cheroliv.opticfiber.service.BackupService
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

@Slf4j
@Component
@TypeChecked
class FiberInitializer implements InitializingBean {

    final BackupService backupService

    FiberInitializer(BackupService service) {
        backupService = service
    }

    @Override
    void afterPropertiesSet() throws Exception {
        String className = this.class.simpleName.toUpperCase()
        String methodName = '.afterPropertiesSet()'
        log.info(className+methodName+" start : ${backupService.class.simpleName}.loadBackupInDatabase()}")
        try {
            backupService.loadBackupInDatabase()
        } catch (Throwable e) {
            throw new FiberInitializerException(cause: e)
        }
        log.info(className+methodName+" end : ${backupService.class.simpleName}.loadBackupInDatabase()}")

    }

}