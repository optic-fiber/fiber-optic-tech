package com.cheroliv.opticfiber.controller.exceptions


import org.springframework.web.bind.annotation.ResponseStatus

import static org.springframework.http.HttpStatus.NOT_FOUND

@ResponseStatus(code = NOT_FOUND, reason = "InterEntity Not Found")
class InterResourceNotFoundException extends RuntimeException {
}
