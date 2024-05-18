package ru.vsu.cs.tp.freelanceFinderServer.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import ru.vsu.cs.tp.freelanceFinderServer.dto.OrderDTO
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.service.OrderService

@RestController
@RequestMapping("/api/client")
class ClientController(
    private val orderService: OrderService
) {

    @PostMapping("/createOrder")
    @ResponseStatus(HttpStatus.CREATED)
    fun addOrder(@RequestBody orderDto: OrderDTO, @RequestHeader("Authorization") token: String): Order {
        return orderService.addOrder(orderDto, token)
    }


}