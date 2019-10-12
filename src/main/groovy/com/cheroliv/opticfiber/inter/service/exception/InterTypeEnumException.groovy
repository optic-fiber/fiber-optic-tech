package com.cheroliv.opticfiber.inter.service.exception

class InterTypeEnumException extends RuntimeException {
    InterTypeEnumException(String type) {
        super("Invalid type InterEntity : ${type}")
    }
}
