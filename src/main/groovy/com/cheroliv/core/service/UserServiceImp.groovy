package com.cheroliv.core.service

import com.cheroliv.core.repository.UserRepository

class UserServiceImp implements UserService {

    final UserRepository userRepository

    UserServiceImp(
            UserRepository userRepository) {
        this.userRepository = userRepository
    }
}
