package net.ironpulse.ironpulsessobackend.services.impl

import net.ironpulse.ironpulsessobackend.dto.UserDto
import net.ironpulse.ironpulsessobackend.entities.User
import net.ironpulse.ironpulsessobackend.repositories.UserRepository
import net.ironpulse.ironpulsessobackend.services.IUserService
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val cacheManager: CacheManager
) : IUserService {
    override fun getAllUsers(): List<UserDto> {
        return userRepository.findAll().map{ it.toDto() }.toList()
    }

    override fun getUserByUsername(username: String): UserDto? {
        return userRepository.findUserByUsername(username)?.toDto()
    }

    override fun registerUser(user: User): UserDto? {
        userRepository.findUserByUsername(user.username)?.let {
            return null
        }
        return userRepository.save(user).toDto()
    }

    override fun validateUserLogin(username: String, password: String): Boolean {
        userRepository.findUserByUsername(username)?.let {
            return BCrypt.checkpw(password, it.password)
        }
        return false
    }

    override fun generateAndStoreUserToken(username: String): String {
        val token = UUID.randomUUID().toString()
        cacheManager.getCache("token")?.put(username, token)
        return "$username:$token"
    }
}