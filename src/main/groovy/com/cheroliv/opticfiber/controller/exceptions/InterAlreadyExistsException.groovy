package com.cheroliv.opticfiber.controller.exceptions

import org.springframework.web.bind.annotation.ResponseStatus

import static org.springframework.http.HttpStatus.CONFLICT

@ResponseStatus(code = CONFLICT, reason = "InterEntity unique constraint of nd and type Already Exists")
class InterAlreadyExistsException extends RuntimeException {
}
