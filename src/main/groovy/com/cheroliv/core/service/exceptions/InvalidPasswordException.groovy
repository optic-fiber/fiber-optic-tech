package com.cheroliv.core.service.exceptions

class InvalidPasswordException extends RuntimeException {

    InvalidPasswordException() {
        super("Incorrect password")
    }

}
