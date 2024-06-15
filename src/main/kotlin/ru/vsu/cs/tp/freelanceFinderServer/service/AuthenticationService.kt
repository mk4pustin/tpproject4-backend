package ru.vsu.cs.tp.freelanceFinderServer.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.vsu.cs.tp.freelanceFinderServer.dto.AccountRecoveryRequest
import ru.vsu.cs.tp.freelanceFinderServer.model.User
import ru.vsu.cs.tp.freelanceFinderServer.repository.RoleRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.UserRepository
import ru.vsu.cs.tp.freelanceFinderServer.dto.AuthenticationRequest
import ru.vsu.cs.tp.freelanceFinderServer.dto.AuthenticationResponse
import ru.vsu.cs.tp.freelanceFinderServer.dto.RegisterRequest
import java.time.LocalDateTime
import java.util.*

@Service
class AuthenticationService @Autowired constructor(

    private val repository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val roleRepository: RoleRepository,
    private val mailSender: JavaMailSender

) {

    fun register(request: RegisterRequest): AuthenticationResponse {
        val role = roleRepository.findByName(request.role) ?: throw RuntimeException("Role not found")
        val user = User(
            0,
            role,
            null,
            request.username,
            request.email,
            passwordEncoder.encode(request.password),
            null,
            null,
            null,
            LocalDateTime.now(),
            LocalDateTime.now(),
            null,
            null
        )
        repository.save(user)
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken, user)
    }

    fun authenticate(request: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username, request.password
            )
        )
        val user = repository.findByUsername(request.username).orElseThrow()
        user.lastOnline = LocalDateTime.now()
        repository.save(user)
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken, user)
    }

    fun recoverAccount(request: AccountRecoveryRequest) {
        val user = repository.findById(request.userId).orElseThrow { RuntimeException("User not found") }

        val newPassword = UUID.randomUUID().toString().substring(0, 8)
        user.password = passwordEncoder.encode(newPassword)
        repository.save(user)

        val message = SimpleMailMessage()
        message.from = "mailsenderapp@yandex.ru"
        message.setTo(user.email)
        message.subject = "Восстановление доступа к аккаунту"
        message.text = """
        Уважаемый ${user.username},

        Ваш запрос на восстановление доступа к аккаунту был обработан. Вот ваш новый пароль:

        $newPassword

        Пожалуйста, не забудьте изменить пароль после входа в аккаунт.

        С уважением,
        Команда FreelanceFinder
        """.trimIndent()

        mailSender.send(message)
    }
}