package ru.vsu.cs.tp.freelanceFinderServer.model

import User
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.OneToOne
import jakarta.persistence.ManyToMany
import jakarta.persistence.JoinTable
import jakarta.persistence.JoinColumn
import java.time.LocalDateTime

@Entity
data class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

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
    val scopes: List<Scope>,

    val price: Double,

    val description: String,

    val creationDate: LocalDateTime,

    val responsesCount: Int,

    val status: String,

    val skills: String

)