package net.ironpulse.ironpulsessobackend.controllers

import jakarta.validation.Validator
import net.ironpulse.ironpulsessobackend.entities.User
import net.ironpulse.ironpulsessobackend.forms.LoginForm
import net.ironpulse.ironpulsessobackend.forms.RegisterForm
import net.ironpulse.ironpulsessobackend.interceptors.Role
import net.ironpulse.ironpulsessobackend.services.IUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController (
    private val userService: IUserService,
    private val validator: Validator,
){
    @GetMapping("/users")
    fun getAllUsers() = userService.getAllUsers()

    @Role(role = User.Role.ADMIN)
    @GetMapping("/user/{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<Any> {
        userService.getUserByUsername(username)?.let { return ResponseEntity.ok(it) }
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/user/register")
    fun register(@RequestBody form: RegisterForm): ResponseEntity<Any> {
        val result = validator.validate(form)
        if (result.isNotEmpty())
            return ResponseEntity.badRequest().body(result.map { it.message })
        val password = BCrypt.hashpw(form.password, BCrypt.gensalt())

        userService.registerUser(
            User(
                username = form.username,
                fullName = form.fullName,
                password = password,
                role = User.Role.USER
            )
        )?.let { return ResponseEntity.ok(it) }
        return ResponseEntity.status(HttpStatus.CONFLICT).build()
    }

    @GetMapping("/user/login")
    fun login(@RequestBody form: LoginForm): ResponseEntity<Any> {
        val result = validator.validate(form)
        if (result.isNotEmpty())
            return ResponseEntity.badRequest().body(result.map { it.message })

        if (!userService.validateUserLogin(form.username, form.password))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()

        return ResponseEntity.ok(mapOf("token" to
                userService.generateAndStoreUserToken(form.username)))
    }
}