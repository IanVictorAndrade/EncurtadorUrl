//package tjro.config.seguranca
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import jakarta.servlet.FilterChain
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//import org.springframework.security.authentication.AuthenticationManager
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.Authentication
//import org.springframework.security.core.userdetails.UserDetails
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
//import tjro.service.TokenService
//import tjro.dto.DadosAutenticacao
//
//
//
//class JWTLoginFilter(private val authenticationManager: AuthenticationManager?,
//                     private val tokenService: TokenService
//) : UsernamePasswordAuthenticationFilter() {
//
//
//    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
//        val dadosAutenticacao: DadosAutenticacao =
//            ObjectMapper().readValue(request?.inputStream, DadosAutenticacao::class.java)
//        val token = UsernamePasswordAuthenticationToken(dadosAutenticacao.email, dadosAutenticacao.senha)
//        return authenticationManager?.authenticate(token) ?: throw RuntimeException("token não gerado...")
//    }
//
//    override fun successfulAuthentication(
//        request: HttpServletRequest?,
//        response: HttpServletResponse?,
//        chain: FilterChain?,
//        authResult: Authentication?
//    ) {
//        val email = (authResult?.principal as UserDetails).username
//        val token = tokenService.generateToken(email)
//        response?.addHeader("Authorization", "Bearer $token")
//    }
//
//}
