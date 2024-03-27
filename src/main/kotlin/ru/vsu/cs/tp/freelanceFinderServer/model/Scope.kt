package ru.vsu.cs.tp.freelanceFinderServer.model

import User
import jakarta.persistence.*

@Entity
data class Scope(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "scope_id")
    val scopeId: Long,
    @ManyToMany(mappedBy = "scopes")
    val orderScopes: MutableList<Order>,
    @ManyToMany(mappedBy = "scopes")
    val userScopes: MutableList<User>,
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    val category: Category,
    val name: String
)