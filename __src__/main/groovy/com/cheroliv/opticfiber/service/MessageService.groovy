package com.cheroliv.opticfiber.service


import java.text.MessageFormat

interface MessageService {
    MessageFormat resolveCode(String code, Locale locale)
}