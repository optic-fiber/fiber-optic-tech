package com.cheroliv.opticfiber.service.exceptions

class InterTypeEnumException extends RuntimeException {
    InterTypeEnumException(String type) {
        super("Invalid type InterEntity : ${type}")
    }
}
