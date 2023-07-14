package tjro.service

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service
import tjro.dto.AtualizaUsuario
import tjro.dto.DadosUsuarios
import tjro.dto.UsuarioView
import tjro.entidade.Usuario
import tjro.mapper.UsuarioMapper
import tjro.repositorio.UsuarioRepository
import java.util.*
import java.util.stream.Collectors


@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private var usuarioMapper: UsuarioMapper
) {


    fun listar(): List<UsuarioView> {
        return usuarioRepository.findAll().stream().map { t -> usuarioMapper.map(t) }.collect(Collectors.toList())
    }

    fun buscarPorId(id: Long): Optional<Usuario> {
        return usuarioRepository.findById(id)
    }

    fun edita(edit: AtualizaUsuario) {
        val usuario = usuarioRepository.findById(edit.id).orElseThrow { NotFoundException() }
        usuario.email = edit.email
        usuario.senha = edit.senha
    }

    fun deletar(id: Long) {
        return usuarioRepository.deleteById(id)
    }

    fun cadastrar(dados: DadosUsuarios) {

        var usuario = Usuario(0, "", "")


        if (dados.id != 0.toLong()) {
            throw RuntimeException("campo id tem que ser 0")
        }


        @Suppress("KotlinConstantConditions")
        usuario.id = dados.id
        usuario.email = dados.email
        usuario.senha = dados.senha


        val existeUsuario = usuarioRepository.encontreEmail(dados.email)
        if (existeUsuario == null) {
            usuarioRepository.save(usuario)
        }
    }
}