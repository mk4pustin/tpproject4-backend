package ru.vsu.cs.tp.freelanceFinderServer.model

import User
import jakarta.persistence.*

@Entity
data class OrderComment(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_comment_id")
    val orderCommentId: Long,

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    val order: Order,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    val user: User,

    val rating: Int,

    val description: String
)