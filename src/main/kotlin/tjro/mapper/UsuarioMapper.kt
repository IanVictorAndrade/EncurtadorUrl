package tjro.mapper

import org.springframework.stereotype.Component
import tjro.dto.UsuarioView
import tjro.entidade.Usuario

@Component
class UsuarioMapper: Mapper<Usuario, UsuarioView> {


    override fun map(t: Usuario): UsuarioView {
        return UsuarioView(
            id = t.id,
            email = t.email,
            senha = t.senha
        )
    }
}