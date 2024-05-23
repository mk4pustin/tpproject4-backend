package ru.vsu.cs.tp.freelanceFinderServer.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.OneToOne
import jakarta.persistence.ManyToMany
import jakarta.persistence.JoinTable
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "AppOrder")
data class Order(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne(mappedBy = "lastOrder")
    @JoinColumn(name = "freelancer_id", referencedColumnName = "id")
    val freelancer: User?,

    @OneToOne
    @JoinColumn(name = "orderer_id", referencedColumnName = "id")
    val orderer: User,

    @ManyToMany
    @JoinTable(
        name = "OrderScope",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "scope_id")]
    )
    var scopes: List<Scope>,

    var price: Double,

    var description: String,

    val creationDate: LocalDateTime,

    val responsesCount: Int,

    val status: String,

    var skills: String

)