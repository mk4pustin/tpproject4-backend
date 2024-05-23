package ru.vsu.cs.tp.freelanceFinderServer.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.vsu.cs.tp.freelanceFinderServer.dto.ClaimDTO
import ru.vsu.cs.tp.freelanceFinderServer.dto.UserDTO
import ru.vsu.cs.tp.freelanceFinderServer.model.Claim
import ru.vsu.cs.tp.freelanceFinderServer.model.User
import ru.vsu.cs.tp.freelanceFinderServer.service.UserService

@RestController
@RequestMapping("/api/user")
@Tag(name = "Контроллер для аутентифицированных пользователей", description = "API для создания заявок и обновления профиля пользователя")
class AuthenticatedUsersController(
    private val userService: UserService
) {

    @PostMapping("/createClaim")
    @Operation(summary = "Создание жалобы", description = "Создает новую жалобу")
    @ResponseStatus(HttpStatus.CREATED)
    fun addClaim(@RequestBody claimDto: ClaimDTO, @RequestHeader("Authorization") token: String): Claim {
        return userService.addClaim(claimDto, token)
    }

    @PostMapping("/updateProfile")
    @Operation(summary = "Обновление профиля", description = "Обновляет профиль аутентифицированного пользователя")
    @ResponseStatus(HttpStatus.OK)
    fun updateProfile(@RequestBody userDto: UserDTO, @RequestHeader("Authorization") token: String): User {
        return userService.updateUserProfile(userDto, token)
    }

}