package ru.vsu.cs.tp.freelanceFinderServer.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
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
    private val jwtService: JwtService,
    private val passwordEncoder: PasswordEncoder,

    ) {

    fun getFreelancers(): List<User> {
        return userRepository.findAllByRoleName("Freelancer")
    }

    fun getUserById(id: Long): User {
        return userRepository.findById(id).orElseThrow()
    }

    fun updateUserProfile(userDto: UserDTO, token: String): User {
        val user = jwtService.getAuthenticatedUser(token)

        userDto.username?.takeIf { it.isNotEmpty() }?.let { user.username = it }
        userDto.email?.takeIf { it.isNotEmpty() }?.let { user.email = it }
        userDto.password?.takeIf { it.isNotEmpty() }?.let { user.password = passwordEncoder.encode(it) }
        userDto.aboutMe?.takeIf { it.isNotEmpty() }?.let { user.aboutMe = it }
        userDto.contact?.takeIf { it.isNotEmpty() }?.let { user.contact = it }
        userDto.skills?.takeIf { it.isNotEmpty() }?.let { user.skills = it }
        userDto.price?.let { if (it.toString().isNotEmpty()) user.price = it }
        userDto.ordersCount?.let { if (it.toString().isNotEmpty()) user.ordersCount = it }

        userDto.scopes?.takeIf { it.isNotEmpty() }?.let { scopeNames ->
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