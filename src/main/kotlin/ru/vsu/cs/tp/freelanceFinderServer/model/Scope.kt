package ru.vsu.cs.tp.freelanceFinderServer.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany

@Entity
data class Scope(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToMany(mappedBy = "scopes")
    val orderScopes: List<Order>,

    @ManyToMany(mappedBy = "scopes")
    val userScopes: List<User>,

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    val category: Category,

    val name: String

)