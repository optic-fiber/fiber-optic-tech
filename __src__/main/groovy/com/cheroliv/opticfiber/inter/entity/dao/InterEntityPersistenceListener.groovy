package com.cheroliv.opticfiber.inter.entity.dao

import groovy.util.logging.Slf4j

import javax.persistence.*

@Slf4j
class InterEntityPersistenceListener {
    @PrePersist
    void prePersist() {
        log.info "prePersist InterEntity"
    }

    @PostPersist
    void postPersist() {
        log.info "postPersist InterEntity"
    }

    @PreUpdate
    void preUpdate() {
        log.info "preUpdate InterEntity"
    }

    @PostUpdate
    void postUpdate() {
        log.info "postUpdate InterEntity"
    }

    @PreRemove
    void preRemove() {
        log.info "preRemove InterEntity"
    }

    @PostRemove
    void postRemove() {
        log.info "postRemove InterEntity"
    }

    @PostLoad
    void postLoad() {
        log.info "postLoad InterEntity"
    }
}
