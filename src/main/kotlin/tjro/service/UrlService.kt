package tjro.service

import org.hibernate.Length
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException
import java.math.BigInteger
import java.security.MessageDigest


@Service
class UrlService(private val redis: RedisTemplate<String, String>) {

    private val digest = MessageDigest.getInstance("SHA-256")



    fun encurtar(url: String): String {
        val hash = hash(url)
        redis.opsForValue().set(hash, url)


        return hash
    }

    private fun hash(url: String, length: Int = 6): String {
        val bytes = digest.digest(url.toByteArray())
        val hash = String.format("%32x", BigInteger(1, bytes))

        return hash.take(length)
    }

    fun resolveHash(hash: String): String {
        return redis.opsForValue().get(hash) ?: throw HashDesconhecido(hash)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class HashDesconhecido(hash: String) : RuntimeException("$hash não encontrado")


}