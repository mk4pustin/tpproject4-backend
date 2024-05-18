package ru.vsu.cs.tp.freelanceFinderServer.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.vsu.cs.tp.freelanceFinderServer.model.User
import ru.vsu.cs.tp.freelanceFinderServer.repository.RoleRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.UserRepository
import ru.vsu.cs.tp.freelanceFinderServer.dto.AuthenticationRequest
import ru.vsu.cs.tp.freelanceFinderServer.dto.AuthenticationResponse
import ru.vsu.cs.tp.freelanceFinderServer.dto.RegisterRequest
import java.time.LocalDateTime

@Service
class AuthenticationService @Autowired constructor(
    private val repository: UserRepository,
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val roleRepository: RoleRepository
) {

    fun register(request: RegisterRequest): AuthenticationResponse {
        val role = roleRepository.findByName(request.role)
            ?: throw RuntimeException("Role not found")
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
            null,
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
                request.username,
                request.password
            )
        )
        val user = repository.findByUsername(request.username).orElseThrow()
        val jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse(jwtToken, user)
    }

}

