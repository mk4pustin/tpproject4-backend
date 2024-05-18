package ru.vsu.cs.tp.freelanceFinderServer.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.vsu.cs.tp.freelanceFinderServer.dto.ClaimDTO
import ru.vsu.cs.tp.freelanceFinderServer.dto.OrderDTO
import ru.vsu.cs.tp.freelanceFinderServer.model.Claim
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.User
import ru.vsu.cs.tp.freelanceFinderServer.service.UserService

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/orders")
    fun getActiveOrders(): List<Order> {
        return userService.getActiveOrders()
    }

    @GetMapping("/freelancers")
    fun getFreelancers(): List<User> {
        return userService.getFreelancers()
    }

    @GetMapping("/orders/{id}")
    fun getOrderById(@PathVariable id: Long): Order {
        return userService.getOrderById(id)
    }

    @GetMapping("/freelancers/{id}")
    fun getUserById(@PathVariable id: Long): User {
        return userService.getUserById(id)
    }

    @PostMapping("/createClaim")
    @ResponseStatus(HttpStatus.CREATED)
    fun addClaim(@RequestBody claimDto: ClaimDTO, @RequestHeader("Authorization") token: String): Claim {
        return userService.addClaim(claimDto, token)
    }

}
