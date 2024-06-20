package ru.vsu.cs.tp.freelanceFinderServer.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.vsu.cs.tp.freelanceFinderServer.dto.ClaimDTO
import ru.vsu.cs.tp.freelanceFinderServer.dto.OrderCommentDTO
import ru.vsu.cs.tp.freelanceFinderServer.dto.UserDTO
import ru.vsu.cs.tp.freelanceFinderServer.model.Claim
import ru.vsu.cs.tp.freelanceFinderServer.model.OrderComment
import ru.vsu.cs.tp.freelanceFinderServer.model.User
import ru.vsu.cs.tp.freelanceFinderServer.service.ClaimService
import ru.vsu.cs.tp.freelanceFinderServer.service.OrderService
import ru.vsu.cs.tp.freelanceFinderServer.service.UserService

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Контроллер для аутентифицированных пользователей", description = "API для создания заявок и обновления профиля пользователя")
class AuthenticatedUsersController(
    private val userService: UserService,
    private val claimService: ClaimService,
    private val orderService: OrderService
) {

    @PostMapping("/createClaim")
    @Operation(summary = "Создание жалобы", description = "Создает новую жалобу")
    @ResponseStatus(HttpStatus.CREATED)
    fun addClaim(@RequestBody claimDto: ClaimDTO, @RequestHeader("Authorization") token: String): Claim {
        return claimService.addClaim(claimDto, token)
    }

    @PostMapping("/updateProfile")
    @Operation(summary = "Обновление профиля", description = "Обновляет профиль аутентифицированного пользователя")
    @ResponseStatus(HttpStatus.OK)
    fun updateProfile(@RequestBody userDto: UserDTO, @RequestHeader("Authorization") token: String): User {
        return userService.updateUserProfile(userDto, token)
    }

    @PostMapping("/addComment")
    @Operation(summary = "Добавление комментария к заказу", description = "Добавляет комментарий к заказу от исполнителя или заказчика")
    @ResponseStatus(HttpStatus.CREATED)
    fun addComment(@RequestBody orderCommentDto: OrderCommentDTO, @RequestHeader("Authorization") token: String): OrderComment {
        return orderService.addComment(orderCommentDto, token)
    }

    @DeleteMapping("/users/{userId}")
    @Operation(summary = "Удаление пользователя", description = "Удаляет пользователя по его ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteUser(@PathVariable userId: Long) {
        userService.deleteUser(userId)
    }

}