package ru.vsu.cs.tp.freelanceFinderServer.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.OrderComment

interface OrderCommentRepository : JpaRepository<OrderComment, Long> {

    fun findByOrder(order: Order): List<OrderComment>

}