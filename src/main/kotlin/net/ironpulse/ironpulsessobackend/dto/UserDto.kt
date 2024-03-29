package net.ironpulse.ironpulsessobackend.dto

import net.ironpulse.ironpulsessobackend.entities.User

data class UserDto(val username: String, val role: User.Role)