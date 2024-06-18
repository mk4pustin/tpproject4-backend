package ru.vsu.cs.tp.freelanceFinderServer.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import jakarta.persistence.Column
import jakarta.persistence.Table
import jakarta.persistence.OneToOne
import jakarta.persistence.ManyToMany
import jakarta.persistence.JoinTable
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.Collections

@Entity
@Table(name = "AppUser")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    val role: Role,

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "last_order_id", referencedColumnName = "id")
    var lastOrder: Order? = null,

    @JvmField
    @Column(name = "username", nullable = false)
    var username: String,

    @Column(nullable = false, unique = true)
    var email: String,

    @JvmField
    @JsonIgnore
    @Column(name = "password", nullable = false)
    var password: String,

    @ManyToMany
    @JoinTable(
        name = "UserScope",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "scope_id")]
    )
    var scopes: List<Scope>? = null,

    var aboutMe: String? = null,

    var contact: String? = null,

    val registrationDate: LocalDateTime,

    var lastOnline: LocalDateTime? = null,

    val rating: Double? = null,

    var skills: String? = null

): UserDetails {

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    @JsonIgnore
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return Collections.singletonList(role)
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

}
