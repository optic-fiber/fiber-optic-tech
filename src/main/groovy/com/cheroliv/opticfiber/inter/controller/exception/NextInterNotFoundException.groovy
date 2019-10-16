package com.cheroliv.opticfiber.inter.controller.exception

import org.springframework.web.bind.annotation.ResponseStatus

import static org.springframework.http.HttpStatus.NOT_FOUND

@ResponseStatus(code = NOT_FOUND, reason = "Next InterEntity Not Found")
class NextInterNotFoundException extends RuntimeException {
}
