package ru.vsu.cs.tp.freelanceFinderServer.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.User

interface OrderRepository : JpaRepository<Order, Long> {

    fun findAllByStatus(status: String): List<Order>

    fun findAllByFreelancer(freelancer: User): List<Order>

    fun findAllByOrderer(orderer: User): List<Order>

}