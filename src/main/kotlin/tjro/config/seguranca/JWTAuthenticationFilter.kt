//package tjro.config.seguranca
//
//import jakarta.servlet.FilterChain
//import jakarta.servlet.http.HttpServletRequest
//import jakarta.servlet.http.HttpServletResponse
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.web.filter.OncePerRequestFilter
//import tjro.service.TokenService
//
//class JWTAuthenticationFilter(private val tokenService: TokenService) : OncePerRequestFilter() {
//
//    override fun doFilterInternal(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//        val token = request.getHeader("Authorization")
//        val jwt = getTokenDetail(token)
//
//        if (tokenService.isValid(jwt)){
//            val authentication = tokenService.getAuthentication(jwt)
//            SecurityContextHolder.getContext().authentication = authentication
//        }
//        filterChain.doFilter(request, response)
//    }
//
//    private fun getTokenDetail(token: String?): String? {
//        return token?.let { jwt ->
//            jwt.startsWith("Bearer ")
//            jwt.substring(7, jwt.length)
//        }
//
//    }
//
//}
