package com.cheroliv.opticfiber.inter.domain

import com.cheroliv.opticfiber.config.InterConstants
import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.ToString

import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import java.time.LocalDateTime

@ToString
class InterDto implements Serializable {
    InterDto() {
    }
    Long id
    @NotNull(message = InterConstants.ND_NOTNULL_CSTRT_TPL_MSG)
    @Size(min = 10, max = 10,
            message = InterConstants.ND_SIZE_CSTRT_TPL_MSG)
    String nd
    @Size(max = 100)
    String firstName
    @Size(max = 100)
    String lastName
    @NotNull
    @Pattern(regexp = "LM|IQ|CABLE_ROUTING")
    String contract
    @NotNull
    @Pattern(regexp = "BAAP|BAOC|BAFA|BAST|PLP|SAV")
    String typeInter
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime dateTime
}
