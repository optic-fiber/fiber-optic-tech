package com.cheroliv.opticfiber.controller.exceptions

import org.springframework.web.bind.annotation.ResponseStatus

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

@ResponseStatus(code = UNPROCESSABLE_ENTITY,
        reason = "InterEntity id null before patch")
class InterIdNullBeforePatchException extends RuntimeException {
}
