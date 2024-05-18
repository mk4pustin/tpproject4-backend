package ru.vsu.cs.tp.freelanceFinderServer.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vsu.cs.tp.freelanceFinderServer.dto.OrderDTO
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.repository.OrderRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.ScopeRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.UserRepository
import java.time.LocalDateTime

@Service
@Transactional
class OrderService(
    private val orderRepository: OrderRepository,
    private val scopeRepository: ScopeRepository,
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {

    fun addOrder(orderDto: OrderDTO, token: String): Order {
        val scopes = scopeRepository.findAllByNameIn(orderDto.scopes)
        val curJwt = token.substring(7)
        val username = jwtService.extractUsername(curJwt)
        val user = userRepository.findByUsername(username)
        val order = Order(
            0,
            null,
            user.orElseThrow(),
            scopes,
            orderDto.price,
            orderDto.description,
            LocalDateTime.now(),
            0,
            "Active",
            orderDto.skills
        )
        return orderRepository.save(order)
    }

}

