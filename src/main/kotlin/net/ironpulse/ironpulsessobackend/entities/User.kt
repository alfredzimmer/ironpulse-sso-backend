package net.ironpulse.ironpulsessobackend.entities

import jakarta.persistence.*
import net.ironpulse.ironpulsessobackend.dto.UserDto


@Entity
@Table(name = "user_table")
class User(
    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    val username: String,

    val fullName: String,

    @Column(nullable = false)
    val password: String,

    private val role: Role,
) {
    fun toDto() = UserDto(username, role)
    enum class Role(val priority: Int) {
        ADMIN(0),
        USER(1),
    }
}