package tjro.entidade

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Usuario (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var email: String,
    var senha: String,
    var token: String


)
