package ru.vsu.cs.tp.freelanceFinderServer.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.vsu.cs.tp.freelanceFinderServer.dto.CompleteClaimDTO
import ru.vsu.cs.tp.freelanceFinderServer.model.Claim
import ru.vsu.cs.tp.freelanceFinderServer.service.ClaimService
import ru.vsu.cs.tp.freelanceFinderServer.service.OrderService
import ru.vsu.cs.tp.freelanceFinderServer.service.UserService

@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Контроллер для администраторов", description = "API для обработки жалоб и удаления запрещенного контента")
class AdminController(

    private val userService: UserService,
    private val claimService: ClaimService,
    private val orderService: OrderService,

    ) {

    @GetMapping("/claims")
    @Operation(summary = "Получение жалоб", description = "Возвращает список жалоб")
    fun getClaims(): List<Claim> {
        return claimService.getClaims()
    }

    @PatchMapping("/completeClaim/{claimId}")
    @Operation(summary = "Обработка жалобы", description = "Обновляет статус жалобы и добавляет комментарий администратора")
    fun updateClaim(
        @PathVariable claimId: Long,
        @RequestBody completeClaimDto: CompleteClaimDTO,
        @RequestHeader("Authorization") token: String
    ): Claim {
        return claimService.completeClaim(claimId, completeClaimDto, token)
    }

    @DeleteMapping("/orders/{orderId}")
    @Operation(summary = "Удаление заказа", description = "Удаляет заказ по его ID")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrder(@PathVariable orderId: Long) {
        orderService.deleteOrderByAdmin(orderId)
    }

}