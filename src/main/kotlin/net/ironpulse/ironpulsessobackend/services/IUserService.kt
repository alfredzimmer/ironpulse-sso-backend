package net.ironpulse.ironpulsessobackend.services

import net.ironpulse.ironpulsessobackend.dto.UserDto
import net.ironpulse.ironpulsessobackend.entities.User

interface IUserService {
    fun getAllUsers(): List<UserDto>

    fun getUserByUsername(username: String): UserDto?

    fun registerUser(user: User): UserDto?

    fun validateUserLogin(username: String, password: String): Boolean

    fun generateAndStoreUserToken(username: String): String
}