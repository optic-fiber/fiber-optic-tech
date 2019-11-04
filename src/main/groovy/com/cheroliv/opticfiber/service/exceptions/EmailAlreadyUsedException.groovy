package com.cheroliv.opticfiber.service.exceptions

class EmailAlreadyUsedException extends RuntimeException {

    EmailAlreadyUsedException() {
        super("Email is already in use!")
    }

}
