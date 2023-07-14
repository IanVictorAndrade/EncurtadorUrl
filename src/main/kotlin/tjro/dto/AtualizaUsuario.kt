package tjro.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class AtualizaUsuario(

    @field:NotNull
    val id: Long,
    @field:NotEmpty
    val email: String,
    @field:NotEmpty
    val senha: String
)
