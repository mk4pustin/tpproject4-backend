package ru.vsu.cs.tp.freelanceFinderServer.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.Response
import ru.vsu.cs.tp.freelanceFinderServer.model.User

interface ResponseRepository : JpaRepository<Response, Long> {

    fun existsByUserAndOrder(user: User, order: Order): Boolean

}
