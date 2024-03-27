import jakarta.persistence.*
import ru.vsu.cs.tp.freelanceFinderServer.model.Scope

@Entity
data class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    val roleId: Long,
    val name: String,
)
