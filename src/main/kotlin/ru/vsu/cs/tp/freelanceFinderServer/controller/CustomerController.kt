package ru.vsu.cs.tp.freelanceFinderServer.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.vsu.cs.tp.freelanceFinderServer.dto.OrderDTO
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.Response
import ru.vsu.cs.tp.freelanceFinderServer.service.OrderService

@RestController
@RequestMapping("/api/customer")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Контроллер для заказчиков", description = "API для создания, обновления и удаления заказов заказчиком")
class CustomerController(
    private val orderService: OrderService
) {

    @PostMapping("/createOrder")
    @Operation(summary = "Создание заказа", description = "Создает новый заказ для аутентифицированного заказчика")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOrder(@RequestBody orderDto: OrderDTO, @RequestHeader("Authorization") token: String): Order {
        return orderService.addOrder(orderDto, token)
    }

    @PostMapping("/updateOrder")
    @Operation(summary = "Обновление заказа", description = "Обновляет существующий заказ для аутентифицированного заказчика")
    @ResponseStatus(HttpStatus.OK)
    fun updateOrder(@RequestBody orderDto: OrderDTO, @RequestHeader("Authorization") token: String): Order {
        return orderService.updateOrder(orderDto, token)
    }

    @PostMapping("/deleteOrder")
    @Operation(summary = "Удаление заказа", description = "Удаляет существующий заказ для аутентифицированного заказчика")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteOrder(@RequestHeader("Authorization") token: String) {
        orderService.deleteOwnOrder(token)
    }

    @PostMapping("/respondToRequest/{responseId}")
    @Operation(summary = "Решение запроса", description = "Подтверждает или отклоняет запрос фрилансера на выполнение заказа")
    @ResponseStatus(HttpStatus.OK)
    fun respondToRequest(
        @PathVariable responseId: Long,
        @RequestParam decision: Boolean,
        @RequestHeader("Authorization") token: String
    ): Response {
        return orderService.respondToRequest(responseId, decision, token)
    }

    @GetMapping("/myOrders")
    @Operation(summary = "Получение всех заказов", description = "Получает все заказы, где пользователь является заказчиком")
    @ResponseStatus(HttpStatus.OK)
    fun getMyOrders(@RequestHeader("Authorization") token: String): List<Order> {
        return orderService.getOrdersByCustomer(token)
    }

}