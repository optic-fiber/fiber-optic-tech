package com.cheroliv.core.domain


import groovy.transform.TypeChecked

@TypeChecked
class PasswordChangeDto {
    String currentPassword
    String newPassword
}
