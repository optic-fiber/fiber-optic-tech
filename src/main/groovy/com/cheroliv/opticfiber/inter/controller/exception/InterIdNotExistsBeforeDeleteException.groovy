package com.cheroliv.opticfiber.inter.controller.exception

import org.springframework.web.bind.annotation.ResponseStatus

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

@ResponseStatus(code = UNPROCESSABLE_ENTITY,
        reason = "InterEntity id not exists before delete")
class InterIdNotExistsBeforeDeleteException extends RuntimeException{
}
