package com.cheroliv.opticfiber.domain


import com.fasterxml.jackson.annotation.JsonFormat
import groovy.transform.ToString

import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import java.time.LocalDateTime

import static com.cheroliv.opticfiber.config.ApplicationConstants.ND_NOTNULL_CSTRT_TPL_MSG
import static com.cheroliv.opticfiber.config.ApplicationConstants.ND_SIZE_CSTRT_TPL_MSG

@ToString
class InterDto implements Serializable {
    Long id
    @NotNull(message = ND_NOTNULL_CSTRT_TPL_MSG)
    @Size(min = 10, max = 10,
            message = ND_SIZE_CSTRT_TPL_MSG)
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
