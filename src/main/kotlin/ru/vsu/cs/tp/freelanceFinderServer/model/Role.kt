package ru.vsu.cs.tp.freelanceFinderServer.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.security.core.GrantedAuthority

@Entity
data class Role(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    val name: String,

): GrantedAuthority {

    override fun getAuthority(): String {
        return name
    }

}
