package com.cheroliv.core.domain

import com.cheroliv.core.entity.UserEntity
import groovy.transform.ToString
import groovy.transform.TypeChecked

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size
import java.time.Instant

@ToString
@TypeChecked
class UserDto implements Serializable {
    Long id
    @NotBlank
    @Pattern(regexp = UserEntity.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    String login
    @Size(max = 50)
    String firstName
    @Size(max = 50)
    String lastName
    @Email
    @Size(min = 5, max = 254)
    String email
    @Size(max = 256)
    String imageUrl
    boolean activated = false
    @Size(min = 2, max = 10)
    String langKey
    String createdBy
    Instant createdDate
    String lastModifiedBy
    Instant lastModifiedDate
    Set<String> authorities
    String activationKey
    Instant resetDate
    String resetKey
    String password
}