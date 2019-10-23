package com.cheroliv.opticfiber.inter.controller.exception


import org.springframework.web.bind.annotation.ResponseStatus

import static org.springframework.http.HttpStatus.NOT_FOUND

@ResponseStatus(code = NOT_FOUND, reason = "First InterEntity Not Found")
class FirstInterNotFoundException extends RuntimeException {
}
