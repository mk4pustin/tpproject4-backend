package ru.vsu.cs.tp.freelanceFinderServer.model

import jakarta.persistence.*

@Entity
data class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val name: String,

    @OneToMany(mappedBy = "category")
    var scopes: List<Scope>

)