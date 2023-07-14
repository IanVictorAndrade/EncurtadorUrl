//package tjro.service
//
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.core.userdetails.UserDetailsService
//import org.springframework.stereotype.Service
//import tjro.entidade.Usuario
//import tjro.repositorio.UsuarioRepository
//import java.util.*
//
//
//@Service
//class AutenticacaoService(val usuarioRepository: UsuarioRepository): UserDetailsService {
//
//
//    fun buscarPorId(id: Long): Optional<Usuario> {
//        return usuarioRepository.findById(id)
//    }
//
//
//    override fun loadUserByUsername(username: String?): UserDetails {
//        val usuario = usuarioRepository.findByEmail(username) ?: throw RuntimeException()
//        return UserDetail(usuario)
//    }
//}