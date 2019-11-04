package com.cheroliv.opticfiber.service.exceptions

class UsernameAlreadyUsedException extends RuntimeException {

    UsernameAlreadyUsedException() {
        super("Login name already used!")
    }

}