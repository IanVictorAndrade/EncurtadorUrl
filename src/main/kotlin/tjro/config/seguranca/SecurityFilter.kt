package tjro.config.seguranca

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import tjro.repositorio.UsuarioRepository
import tjro.service.TokenService


@Component
class SecurityFilter(private val tokenService: TokenService,
                     private val usuarioRepository: UsuarioRepository
    ) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val tokenJwt: String? = recuperarToken(request)

        if (tokenJwt != null) {
            val subject = tokenService.getSubject(tokenJwt)
            val usuario = usuarioRepository.findByEmail(subject)

            val authentication = UsernamePasswordAuthenticationToken(usuario, null, usuario!!.authorities)

            SecurityContextHolder.getContext().authentication = authentication

        }


        filterChain.doFilter(request, response)
    }

    private fun recuperarToken(request: HttpServletRequest): String? {
        var authorization = request.getHeader("Authorization")

        if (authorization != null) {
            return authorization.replace("Bearer", "").trim()
        }

        return null
    }


}