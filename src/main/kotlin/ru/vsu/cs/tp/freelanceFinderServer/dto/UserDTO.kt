package ru.vsu.cs.tp.freelanceFinderServer.dto

data class UserDTO(

    val username: String?,
    val email: String?,
    val password: String?,
    val aboutMe: String?,
    val contact: String?,
    val skills: String?,
    val scopes: List<String>?,
    var price: Double?,
    var ordersCount: Int?

)