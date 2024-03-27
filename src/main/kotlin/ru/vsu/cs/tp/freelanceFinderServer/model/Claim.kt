package ru.vsu.cs.tp.freelanceFinderServer.model

import User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Claim(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "claim_id")
    val claimId: Long,

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

    @Column(name = "creation_date")
    val creationDate: LocalDateTime,

    val status: String,

    @Column(name = "admin_comment")
    val adminComment: String?
)