package tjro.repositorio

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Repository
import tjro.entidade.Usuario


@Repository
interface UsuarioRepository: JpaRepository<Usuario, Long> {


    fun findByEmail(email: String?): Usuario?

    @Query("Select email from Usuario where email = :email")
    fun encontreEmail(email: String): Usuario?

    fun findByToken(token: String?): Usuario
}