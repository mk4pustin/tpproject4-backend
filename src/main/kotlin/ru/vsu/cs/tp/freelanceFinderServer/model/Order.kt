package ru.vsu.cs.tp.freelanceFinderServer.model

import User
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val orderId: Long,

    @OneToOne(mappedBy = "lastOrder")
    @JoinColumn(name = "orderer_id", referencedColumnName = "user_id")
    val freelancer: User?,

    @OneToOne
    @JoinColumn(name = "orderer_id", referencedColumnName = "user_id")
    val orderer: User,

    @ManyToMany
    @JoinTable(
        name = "OrderScope",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "scope_id")]
    )
    val scopes: MutableList<Scope>,
    val price: Double,
    val description: String,
    @Column(name = "creation_date")
    val creationDate: LocalDateTime,
    @Column(name = "responses_count")
    val responsesCount: Int,
    val status: String,
    val skills: String
)