package ru.vsu.cs.tp.freelanceFinderServer.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.tp.freelanceFinderServer.model.Role

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByName(name: String): Role?

}