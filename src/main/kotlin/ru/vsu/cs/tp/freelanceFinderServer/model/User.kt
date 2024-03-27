import jakarta.persistence.*
import ru.vsu.cs.tp.freelanceFinderServer.model.Order
import ru.vsu.cs.tp.freelanceFinderServer.model.Response
import ru.vsu.cs.tp.freelanceFinderServer.model.Scope
import java.time.LocalDateTime

@Entity
@Table(name = "AppUser")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val userId: Long,

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
    val scopes: MutableList<Scope>,

    @Column(name = "about_me")
    val aboutMe: String?,

    val contact: String?,

    @Column(name = "registration_date")
    val registrationDate: LocalDateTime,

    @Column(name = "last_online")
    val lastOnline: LocalDateTime?,

    val rating: Double?,

    val skills: String?
)