package com.cheroliv.opticfiber.inter.service.exception

class InterNotFoundException extends RuntimeException {
    InterNotFoundException(String message) {
        super(message)
    }

    InterNotFoundException(String nd, String type) {
        super("unknown inter(nd: $nd, typeInter: $type")
    }
}