package net.ironpulse.ironpulsessobackend.repositories

import net.ironpulse.ironpulsessobackend.entities.User
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<User, Long> {
    fun findUserByUsername(username: String): User?
}