package com.cheroliv.core.domain

import groovy.transform.ToString

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ToString
class AuthorityDto implements Serializable {
    @NotNull
    @Size(max = 50)
    String name
}
