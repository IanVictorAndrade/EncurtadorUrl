package tjro

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tjro.service.UrlService
import java.net.URI

@RestController
class UrlController(private val urlService: UrlService) {

    @PostMapping("/")
    fun shorten(@RequestBody request: UrlRequest): UrlResponse {
        val hash = urlService.encurtar(request.url)
        return UrlResponse(hash, "http://172.12.22.132:8080/$hash")
    }

    @GetMapping("/{hash}")
    fun resolve(@PathVariable hash: String): ResponseEntity<HttpStatus> {
        val target = urlService.resolveHash(hash)

        return ResponseEntity
            .status(HttpStatus.MOVED_PERMANENTLY)
            .location(URI.create(target))
            .header(HttpHeaders.CONNECTION, "close")
            .build()
    }


}