package ru.vsu.cs.tp.freelanceFinderServer.controller

import io.jsonwebtoken.ExpiredJwtException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.vsu.cs.tp.freelanceFinderServer.model.User
import ru.vsu.cs.tp.freelanceFinderServer.service.AuthenticationService
import ru.vsu.cs.tp.freelanceFinderServer.service.JwtService
import ru.vsu.cs.tp.freelanceFinderServer.dto.AuthenticationRequest
import ru.vsu.cs.tp.freelanceFinderServer.dto.RegisterRequest

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Контроллер аутентификации", description = "API для аутентификации и регистрации пользователей")
class AuthenticationController @Autowired constructor(
    private val authenticationService: AuthenticationService,
    private val jwtService: JwtService,
) {

    @PostMapping("/registration")
    @Operation(summary = "Регистрация пользователя", description = "Регистрирует нового пользователя и возвращает токен авторизации и данные пользователя")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<*> {
        return try {
            val register = authenticationService.register(request)
            ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, register.token)
                .header("Access-Control-Expose-Headers", HttpHeaders.AUTHORIZATION)
                .body(register.user)
        } catch (ex: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build<String>()
        }
    }

    @PostMapping("/authentication")
    @Operation(summary = "Аутентификация пользователя", description = "Аутентифицирует пользователя по логину и паролю и возвращает токен авторизации и данные пользователя")
    fun authenticate(@RequestBody request: AuthenticationRequest): ResponseEntity<*> {
        return try {
            val authenticate = authenticationService.authenticate(request)
            ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authenticate.token)
                .header("Access-Control-Expose-Headers", HttpHeaders.AUTHORIZATION)
                .body(authenticate.user)
        } catch (ex: BadCredentialsException) {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build<String>()
        }
    }

    @GetMapping("/validation")
    @Operation(summary = "Проверка токена", description = "Проверяет действительность токена авторизации")
    fun validateToken(@RequestParam token: String, @AuthenticationPrincipal user: User): ResponseEntity<*> {
        return try {
            val isTokenValid = jwtService.isTokenValid(token, user)
            ResponseEntity.ok(isTokenValid)
        } catch (e: ExpiredJwtException) {
            ResponseEntity.ok(false)
        }
    }

}