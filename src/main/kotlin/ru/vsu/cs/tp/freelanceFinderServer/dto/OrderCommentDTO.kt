package ru.vsu.cs.tp.freelanceFinderServer.dto

data class OrderCommentDTO(
    val orderId: Long,
    val rating: Int,
    val description: String
)