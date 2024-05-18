package ru.vsu.cs.tp.freelanceFinderServer.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.vsu.cs.tp.freelanceFinderServer.dto.ClaimDTO
import ru.vsu.cs.tp.freelanceFinderServer.model.Claim
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.User
import ru.vsu.cs.tp.freelanceFinderServer.repository.ClaimRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.OrderRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.UserRepository
import java.time.LocalDateTime

@Service
class UserService @Autowired constructor(

    private val orderRepository: OrderRepository,
    private val userRepository: UserRepository,
    private val claimRepository: ClaimRepository,
    private val jwtService: JwtService

) {

    fun getActiveOrders(): List<Order> {
        return orderRepository.findAllByStatus("Active")
    }

    fun getFreelancers(): List<User> {
        return userRepository.findAllByRoleName("Freelancer")
    }

    fun getOrderById(id: Long): Order {
        return orderRepository.findById(id).orElseThrow()
    }

    fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow()
    }

    fun addClaim(claimDto: ClaimDTO, token: String): Claim {
        val curJwt = token.substring(7)
        val username = jwtService.extractUsername(curJwt)
        val user = userRepository.findByUsername(username)
        val claim = Claim(
            0,
            user.orElseThrow(),
            userRepository.findById(claimDto.userId).orElseThrow(),
            null,
            claimDto.orderId?.let { orderRepository.findById(it).orElseThrow() },
            claimDto.description,
            LocalDateTime.now(),
            "Active",
            null
        )
        return claimRepository.save(claim)
    }

}