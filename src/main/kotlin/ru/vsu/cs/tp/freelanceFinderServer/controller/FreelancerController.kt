package ru.vsu.cs.tp.freelanceFinderServer.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.Response
import ru.vsu.cs.tp.freelanceFinderServer.service.OrderService

@RestController
@RequestMapping("/api/freelancer")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Контроллер для фрилансеров", description = "API для работы с заказами фрилансера")
class FreelancerController(
    private val orderService: OrderService
) {

    @PostMapping("/requestOrder/{orderId}")
    @Operation(summary = "Запрос заказа", description = "Запрашивает у заказчика разрешение на исполение заказа")
    @ResponseStatus(HttpStatus.CREATED)
    fun requestOrder(@PathVariable orderId: Long, @RequestHeader("Authorization") token: String): Response {
        return orderService.requestOrder(orderId, token)
    }

    @GetMapping("/myOrders")
    @Operation(summary = "Получение всех заказов", description = "Получает все заказы, где фрилансер является исполнителем")
    @ResponseStatus(HttpStatus.OK)
    fun getMyOrders(@RequestHeader("Authorization") token: String): List<Order> {
        return orderService.getOrdersByFreelancer(token)
    }

}