package ru.vsu.cs.tp.freelanceFinderServer.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vsu.cs.tp.freelanceFinderServer.dto.OrderCommentDTO
import ru.vsu.cs.tp.freelanceFinderServer.dto.OrderDTO
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.OrderComment
import ru.vsu.cs.tp.freelanceFinderServer.model.Response
import ru.vsu.cs.tp.freelanceFinderServer.repository.*
import java.time.LocalDateTime

@Service
@Transactional
class OrderService @Autowired constructor(

    private val orderRepository: OrderRepository,
    private val scopeRepository: ScopeRepository,
    private val responseRepository: ResponseRepository,
    private val userRepository: UserRepository,
    private val orderCommentRepository: OrderCommentRepository,
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
                orderDto.title,
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

    fun requestOrder(orderId: Long, token: String): Response {
        val user = jwtService.getAuthenticatedUser(token)
        val order = orderRepository.findById(orderId).orElseThrow()
        val response = Response(
            0,
            order = order,
            user = user,
            status = "Requested",
            creationDate = LocalDateTime.now()
        )
        return responseRepository.save(response)
    }

    fun respondToRequest(responseId: Long, decision: Boolean, token: String): Response {
        val customer = jwtService.getAuthenticatedUser(token)
        val response = responseRepository.findById(responseId).orElseThrow()
        val order = response.order

        if (order.orderer != customer) {
            throw RuntimeException("Unauthorized action")
        }

        response.status = if (decision) "Confirmed" else "Rejected"

        if (decision) {
            order.orderer.lastOrder = order
            order.freelancer?.lastOrder = order
            userRepository.save(order.orderer)
            if (order.freelancer != null) {
                userRepository.save(order.freelancer)
            }
        }

        return responseRepository.save(response)
    }

    fun getOrdersByFreelancer(token: String): List<Order> {
        val freelancer = jwtService.getAuthenticatedUser(token)
        return orderRepository.findAllByFreelancer(freelancer)
    }

    fun getOrdersByCustomer(token: String): List<Order> {
        val customer = jwtService.getAuthenticatedUser(token)
        return orderRepository.findAllByOrderer(customer)
    }

    fun addComment(orderCommentDto: OrderCommentDTO, token: String): OrderComment {
        val user = jwtService.getAuthenticatedUser(token)
        val order = orderRepository.findById(orderCommentDto.orderId).orElseThrow()

        if (order.freelancer != user && order.orderer != user) {
            throw RuntimeException("Unauthorized action")
        }

        val orderComment = OrderComment(
            id = 0,
            order = order,
            user = user,
            rating = orderCommentDto.rating,
            description = orderCommentDto.description
        )

        orderCommentRepository.save(orderComment)

        val comments = orderCommentRepository.findByOrder(order)
        val hasFreelancerComment = comments.any { it.user == order.freelancer }
        val hasOrdererComment = comments.any { it.user == order.orderer }

        if (hasFreelancerComment && hasOrdererComment) {
            order.status = "Completed"
            orderRepository.save(order)
        }

        return orderComment
    }

}