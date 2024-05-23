package ru.vsu.cs.tp.freelanceFinderServer.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.vsu.cs.tp.freelanceFinderServer.dto.ClaimDTO
import ru.vsu.cs.tp.freelanceFinderServer.dto.CompleteClaimDTO
import ru.vsu.cs.tp.freelanceFinderServer.model.Claim
import ru.vsu.cs.tp.freelanceFinderServer.repository.ClaimRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.OrderRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.UserRepository
import java.time.LocalDateTime
import java.util.*

@Service
@Transactional
class ClaimService @Autowired constructor(

    private val orderRepository: OrderRepository,
    private val claimRepository: ClaimRepository,
    private val userRepository: UserRepository,
    private val jwtService: JwtService

) {

    fun getClaims(): List<Claim>{
        return claimRepository.findAll()
    }

    fun completeClaim(claimId: Long, completeClaimDTO: CompleteClaimDTO, token: String): Claim {
        val claim = claimRepository.findById(claimId).orElseThrow()
        val admin = jwtService.getAuthenticatedUser(token)
        claim.admin = admin
        claim.status = "Complete"
        claim.adminComment = completeClaimDTO.adminComment
        return claimRepository.save(claim)
    }

    fun addClaim(claimDto: ClaimDTO, token: String): Claim {
        val user = jwtService.getAuthenticatedUser(token)
        val claim = Claim(
            0,
            user,
            claimDto.userId?.let{ userRepository.findById(claimDto.userId).orElseThrow()},
            null,
            claimDto.orderId?.let { orderRepository.findById(claimDto.orderId).orElseThrow() },
            claimDto.description,
            LocalDateTime.now(),
            "Active",
            null
        )
        return claimRepository.save(claim)
    }

}