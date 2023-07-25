package tjro.service


import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import tjro.entidade.Usuario
import java.time.LocalDateTime
import java.time.ZoneOffset


@Service
class TokenService {

    @Value(value = "\${api.security.token.secret}")
    private val secret: String = ""

    fun gerarToken(usuario: Usuario): String? {
        return try {
            val token = JWT.create()
                .withIssuer("API EncurtadorURL")
                .withSubject(usuario.email)
                .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-04:00")))
                .sign(Algorithm.HMAC256(secret))

            token
        } catch (exception: JWTCreationException) {
            throw RuntimeException("erro ao gerar token jwt", exception)
        }
    }

    fun getSubject(tokenJWT: String?): String {
         try {
            val algoritmo = Algorithm.HMAC256(secret)
            return JWT.require(algoritmo)
                .withIssuer("API EncurtadorURL")
                .build()
                .verify(tokenJWT)
                .subject
        } catch (exception: JWTVerificationException) {
            throw java.lang.RuntimeException("Token JWT inválido ou expirado!")
        }
    }




}