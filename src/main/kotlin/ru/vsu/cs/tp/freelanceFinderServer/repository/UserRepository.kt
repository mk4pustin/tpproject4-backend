package ru.vsu.cs.tp.freelanceFinderServer.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.tp.freelanceFinderServer.model.User
import java.util.Optional

interface UserRepository : JpaRepository<User, Int> {

    fun findByUsername(username: String?): Optional<User>

    fun findAllByRoleName(roleName: String?): List<User>

    fun findById(id: Long?): Optional<User>

}

