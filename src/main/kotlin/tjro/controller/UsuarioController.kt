package tjro.controller

import jakarta.validation.Valid
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import tjro.dto.AtualizaUsuario
import tjro.dto.DadosUsuarios
import tjro.dto.UsuarioView
import tjro.entidade.Usuario
import tjro.service.UsuarioService
import java.util.*


@RestController
@RequestMapping("/usuarios")
class UsuarioController(private val usuarioService: UsuarioService) {

    @PostMapping("/cadastro")
    @Transactional
    fun cadastrarUsuario(@RequestBody dados: DadosUsuarios) {
        return usuarioService.cadastrar(dados)
    }

    @GetMapping("/listar")
    fun listarUsuarios(): List<UsuarioView> {
        return usuarioService.listar()
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): Optional<Usuario> {
        return usuarioService.buscarPorId(id)
    }

    @PutMapping
    @Transactional
    fun editarUsuario(@RequestBody @Valid edit: AtualizaUsuario) {
        return usuarioService.edita(edit)
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun deletaUsuario(@PathVariable id: Long) {
        return usuarioService.deletar(id)
    }

}