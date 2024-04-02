package ru.vsu.cs.tp.freelanceFinderServer.model

import User
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import java.time.LocalDateTime

@Entity
data class Claim(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "initiator_id", referencedColumnName = "user_id")
    val initiator: User,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    val user: User?,

    @ManyToOne
    @JoinColumn(name = "admin_id", referencedColumnName = "user_id")
    val admin: User?,

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    val order: Order?,

    val description: String,

    val creationDate: LocalDateTime,

    val status: String,

    val adminComment: String?

)