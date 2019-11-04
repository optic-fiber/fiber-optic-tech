package com.cheroliv.opticfiber.domain


import groovy.transform.TypeChecked

@TypeChecked
class PasswordChangeDto {
    String currentPassword
    String newPassword
}
