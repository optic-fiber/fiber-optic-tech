package com.cheroliv.opticfiber.service

import com.cheroliv.opticfiber.domain.UserDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserService {
    Optional<UserDto> activateRegistration(String key)
    Optional<UserDto> completePasswordReset(String newPassword, String key)
    Optional<UserDto> requestPasswordReset(String mail)
    UserDto registerUser(UserDto userDto, String password)
    UserDto createUser(UserDto userDto)
    void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl)
    Optional<UserDto> updateUser(UserDto userDto)
    void deleteUser(String login)
    void changePassword(String currentClearTextPassword, String newPassword)
    Page<UserDto> getAllManagedUsers(Pageable pageable)
    Optional<UserDto> getUserWithAuthoritiesByLogin(String login)
    Optional<UserDto> getUserWithAuthorities(Long id)
    Optional<UserDto> getUserWithAuthorities()
    void removeNotActivatedUsers()
    List<String> getAuthorities()
}
