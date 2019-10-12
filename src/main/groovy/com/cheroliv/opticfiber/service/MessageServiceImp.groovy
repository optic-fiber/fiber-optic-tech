package com.cheroliv.opticfiber.service


import org.springframework.context.support.AbstractMessageSource
import org.springframework.stereotype.Service

import java.text.MessageFormat


@Service
class MessageServiceImp extends AbstractMessageSource implements MessageService {

    @Override
    MessageFormat resolveCode(final String code, final Locale locale) {
        null
    }
}
