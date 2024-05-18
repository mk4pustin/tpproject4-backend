package ru.vsu.cs.tp.freelanceFinderServer.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.tp.freelanceFinderServer.model.Order

interface OrderRepository : JpaRepository<Order, Long> {

    fun findAllByStatus(status: String): List<Order>

}
