package tjro.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import tjro.requests.AlterarSenhaRequest
import tjro.dto.AtualizaUsuario
import tjro.dto.DadosUsuarios
import tjro.dto.UsuarioView
import tjro.entidade.Usuario
import tjro.response.GenericResponse
import tjro.service.UsuarioService
import java.util.*


@RestController
@RequestMapping("/usuarios")
class UsuarioController(
    private val usuarioService: UsuarioService
    ) {

    @PostMapping("/cadastro")
    @Transactional
    fun cadastrarUsuario(@RequestBody dados: DadosUsuarios, uriComponentsBuilder: UriComponentsBuilder): ResponseEntity<GenericResponse> {
        usuarioService.cadastrar(dados)
        val uri = uriComponentsBuilder.path("/usuarios/{id}").buildAndExpand(dados.id).toUri()
        val resposta = GenericResponse("Usuário cadastrado com sucesso!")
        return ResponseEntity.created(uri).body(resposta)
    }

    @GetMapping("/listar")
    @SecurityRequirement(name = "bearer-key")
    fun listarUsuarios(): ResponseEntity<List<UsuarioView>> {
        val lista = usuarioService.listar()
        return ResponseEntity.ok(lista)
    }

    @GetMapping("/{id}")
    @SecurityRequirement(name = "bearer-key")
    fun buscarPorId(@PathVariable id: Long): Optional<Usuario> {
        return usuarioService.buscarPorId(id)
    }

    @PutMapping
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    fun editarUsuario(@RequestBody @Valid edit: AtualizaUsuario): ResponseEntity<String> {
        usuarioService.edita(edit)
        return ResponseEntity.ok("Usuário Editado com sucesso!")
    }

    @DeleteMapping("/{id}")
    @Transactional
    @SecurityRequirement(name = "bearer-key")
    fun deletaUsuario(@PathVariable id: Long): ResponseEntity<HttpStatus> {
        usuarioService.deletar(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/codigo-senha")
    @Transactional
    fun gerarToken(@RequestBody email: String): String {
        return usuarioService.enviandoEmailDeRecuperacao(email)
    }
    @PutMapping("/alterar-senha")
    @Transactional
    fun alterarSenha(@RequestBody request: AlterarSenhaRequest): String {
        return usuarioService.redefinirSenha(request.token, request.novaSenha)
    }

}