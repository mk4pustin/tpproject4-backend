package ru.vsu.cs.tp.freelanceFinderServer.model

import User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Response(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "response_id")
    val responseId: Long,

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    val order: Order,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    val user: User,

    val status: String,

    @Column(name = "creation_date")
    val creationDate: LocalDateTime
)