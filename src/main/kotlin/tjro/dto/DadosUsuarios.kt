package tjro.dto

import jakarta.validation.constraints.Email

data class DadosUsuarios (
    val id: Long,
    val email: String,
    val senha: String
)