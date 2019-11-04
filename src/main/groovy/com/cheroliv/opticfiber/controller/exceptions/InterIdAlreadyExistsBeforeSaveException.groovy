package com.cheroliv.opticfiber.controller.exceptions

import org.springframework.web.bind.annotation.ResponseStatus

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY


@ResponseStatus(code = UNPROCESSABLE_ENTITY, reason = "InterEntity Id Already Exists Before Save")
class InterIdAlreadyExistsBeforeSaveException extends RuntimeException {
}
