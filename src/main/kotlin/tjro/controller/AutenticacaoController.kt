package tjro

import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tjro.dto.DadosAutenticacao
import tjro.dto.DadosTokenJWT
import tjro.entidade.Usuario
import tjro.service.TokenService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse




@RestController
@RequestMapping("/login")
class AutenticacaoController(
    @Autowired
    private val manager: AuthenticationManager,
    private val tokenService: TokenService
) {

    @PostMapping
    fun efetuarLogin(@RequestBody @Valid dados: DadosAutenticacao, response: HttpServletResponse): ResponseEntity<DadosTokenJWT> {
        val token = UsernamePasswordAuthenticationToken(dados.email, dados.senha)
        val authentication: Authentication = manager.authenticate(token)
        val tokenJWT = tokenService.gerarToken(authentication.principal as Usuario).toString()
        val dadosTokenJWT = DadosTokenJWT(tokenJWT)
        val cookie = Cookie("token", tokenJWT)
        cookie.domain = "172.22.12.132"
        cookie.path = "/" // Defina o path do cookie conforme necessário
        cookie.isHttpOnly = true // Isso garante que o cookie não seja acessível por JavaScript
        cookie.maxAge = 2 * 60 * 60 // Defina o tempo de expiração do cookie (em segundos) - por exemplo, 24 horas
        response.addCookie(cookie)
        return ResponseEntity.ok(dadosTokenJWT)
    }


}