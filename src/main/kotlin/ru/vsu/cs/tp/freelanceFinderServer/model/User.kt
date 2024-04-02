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
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.Scope
import java.time.LocalDateTime

@Entity
@Table(name = "AppUser")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    val role: Role,

    @OneToOne
    @JoinColumn(name = "last_order_id", referencedColumnName = "order_id")
    val lastOrder: Order,

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val password: String,

    @ManyToMany
    @JoinTable(
        name = "UserScope",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "scope_id")]
    )
    val scopes: List<Scope>,

    val aboutMe: String?,

    val contact: String?,

    val registrationDate: LocalDateTime,

    val lastOnline: LocalDateTime?,

    val rating: Double?,

    val skills: String?

)