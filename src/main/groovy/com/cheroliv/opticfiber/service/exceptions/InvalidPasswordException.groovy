package com.cheroliv.opticfiber.service.exceptions

class InvalidPasswordException extends RuntimeException {

    InvalidPasswordException() {
        super("Incorrect password")
    }

}
