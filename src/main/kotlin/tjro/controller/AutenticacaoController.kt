//package tjro
//
//import jakarta.validation.Valid
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.http.HttpStatus
//import org.springframework.http.ResponseEntity
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.Authentication
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//import tjro.dto.DadosAutenticacao
//
//
//@RestController
//@RequestMapping("/login")
//class AutenticacaoController(
//    @Autowired
//     private val manager: AuthenticationManager
//) {
//
//    @PostMapping
//    fun efetuarLogin(@RequestBody @Valid dados: DadosAutenticacao): ResponseEntity<HttpStatus> {
//        var token = UsernamePasswordAuthenticationToken(dados.email, dados.senha)
//        val authentication: Authentication = manager.authenticate(token)
//        return ResponseEntity.ok().build()
//    }
//
//
//}