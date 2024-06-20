package ru.vsu.cs.tp.freelanceFinderServer.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.vsu.cs.tp.freelanceFinderServer.dto.ClaimDTO
import ru.vsu.cs.tp.freelanceFinderServer.dto.UserDTO
import ru.vsu.cs.tp.freelanceFinderServer.model.Claim
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.User
import ru.vsu.cs.tp.freelanceFinderServer.repository.ClaimRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.OrderRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.ScopeRepository
import ru.vsu.cs.tp.freelanceFinderServer.repository.UserRepository
import java.time.LocalDateTime

@Service
class UserService @Autowired constructor(

    private val userRepository: UserRepository,
    private val scopeRepository: ScopeRepository,
    private val jwtService: JwtService

) {

    fun getFreelancers(): List<User> {
        return userRepository.findAllByRoleName("Freelancer")
    }

    fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow()
    }

    fun updateUserProfile(userDto: UserDTO, token: String): User {
        val user = jwtService.getAuthenticatedUser(token)
        userDto.username?.let { user.username = it }
        userDto.email?.let { user.email = it }
        userDto.password?.let { user.password = it }
        userDto.aboutMe?.let { user.aboutMe = it }
        userDto.contact?.let { user.contact = it }
        userDto.skills?.let { user.skills = it }
        userDto.price?.let { user.price = it }
        userDto.ordersCount?.let { user.ordersCount = it }

        userDto.scopes?.let { scopeNames ->
            val scopes = scopeRepository.findAllByNameIn(scopeNames)
            user.scopes = scopes
        }

        userRepository.save(user)
        return user
    }

    fun deleteUser(userId: Long) {
        val user = userRepository.findById(userId).orElseThrow()
        userRepository.delete(user)
    }

}