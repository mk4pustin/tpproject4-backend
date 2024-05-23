package ru.vsu.cs.tp.freelanceFinderServer.service

import org.springframework.beans.factory.annotation.Autowired
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
class OrderService @Autowired constructor(

    private val orderRepository: OrderRepository,
    private val scopeRepository: ScopeRepository,
    private val userRepository: UserRepository,
    private val jwtService: JwtService

) {

    fun addOrder(orderDto: OrderDTO, token: String): Order {
        val scopes = scopeRepository.findAllByNameIn(orderDto.scopes)
        val curJwt = token.substring(7)
        val username = jwtService.extractUsername(curJwt)
        val user = userRepository.findByUsername(username).orElseThrow()
        val lastOrder = user.lastOrder

        if (lastOrder == null || lastOrder.status == "Complete") {
            val newOrder = Order(
                0,
                null,
                user,
                scopes,
                orderDto.price,
                orderDto.description,
                LocalDateTime.now(),
                0,
                "Active",
                orderDto.skills
            )

            user.lastOrder = newOrder

            orderRepository.save(newOrder)
            userRepository.save(user)

            return newOrder
        } else {
            throw RuntimeException("Cannot create a new order. The last order is not complete.")
        }
    }

    fun updateOrder(orderDto: OrderDTO, token: String): Order {
        val curJwt = token.substring(7)
        val username = jwtService.extractUsername(curJwt)
        val user = userRepository.findByUsername(username).orElseThrow()

        val order = user.lastOrder ?: throw RuntimeException("No order to update")

        val scopes = scopeRepository.findAllByNameIn(orderDto.scopes)
        order.scopes = scopes
        order.price = orderDto.price
        order.description = orderDto.description
        order.skills = orderDto.skills

        orderRepository.save(order)
        return order
    }

    fun deleteOrder(token: String) {
        val curJwt = token.substring(7)
        val username = jwtService.extractUsername(curJwt)
        val user = userRepository.findByUsername(username).orElseThrow()

        val order = user.lastOrder ?: throw RuntimeException("No order to delete")

        orderRepository.delete(order)
        user.lastOrder = null
        userRepository.save(user)
    }

}