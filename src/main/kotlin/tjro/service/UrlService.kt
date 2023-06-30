package tjro.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import tjro.entidade.Url
import tjro.repositorio.UrlRepository
import java.lang.RuntimeException
import java.math.BigInteger
import java.security.MessageDigest


@Service
class UrlService(private val redis: RedisTemplate<String, String>,
                 private val urlRepository: UrlRepository
    ) {

    private val digest = MessageDigest.getInstance("SHA-256")


    fun encurtar(link: String): String {
        val hash = hash(link)
        val urlResolvido = Url(0, link, hash)
        salvarUrl(urlResolvido)


        return urlResolvido.hash
    }

    private fun hash(url: String, length: Int = 6): String {
        val bytes = digest.digest(url.toByteArray())
        val hash = String.format("%32x", BigInteger(1, bytes))

        return hash.take(length)
    }

    fun resolveHash(hash: String): String {
        return redis.opsForValue().get(hash) ?: throw HashDesconhecido(hash)
    }

    fun salvarUrl(url: Url) {
        val urlDB = urlRepository.findByHash(url.hash)
        if (urlDB == null){
            urlRepository.save(url)
        }
        return
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class HashDesconhecido(hash: String) : RuntimeException("$hash não encontrado")



}