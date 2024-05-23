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

    fun getActiveOrders(): List<Order> {
        return orderRepository.findAllByStatus("Active")
    }

    fun getOrderById(id: Long): Order {
        return orderRepository.findById(id).orElseThrow()
    }

    fun addOrder(orderDto: OrderDTO, token: String): Order {
        val scopes = scopeRepository.findAllByNameIn(orderDto.scopes)
        val user = jwtService.getAuthenticatedUser(token)
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
        val user = jwtService.getAuthenticatedUser(token)

        val order = user.lastOrder ?: throw RuntimeException("No order to update")

        val scopes = scopeRepository.findAllByNameIn(orderDto.scopes)
        order.scopes = scopes
        order.price = orderDto.price
        order.description = orderDto.description
        order.skills = orderDto.skills

        orderRepository.save(order)
        return order
    }

    fun deleteOwnOrder(token: String) {
        val user = jwtService.getAuthenticatedUser(token)

        val order = user.lastOrder ?: throw RuntimeException("No order to delete")

        val freelancer = order.freelancer

        orderRepository.delete(order)
        user.lastOrder = null
        if (freelancer != null) {
            freelancer.lastOrder = null
        }
        userRepository.save(user)
        if (freelancer != null) {
            userRepository.save(freelancer)
        }
    }

    fun deleteOrderByAdmin(orderId: Long) {

        val order = orderRepository.findById(orderId)
        val orderer = order.get().orderer
        val freelancer = order.get().freelancer

        orderRepository.delete(order.orElseThrow())
        orderer.lastOrder = null
        if (freelancer != null) {
            freelancer.lastOrder = null
        }

        userRepository.save(orderer)
        if (freelancer != null) {
            userRepository.save(freelancer)
        }
    }

}