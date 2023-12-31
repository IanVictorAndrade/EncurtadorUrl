package tjro.controller

import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tjro.requests.UrlRequest
import tjro.response.UrlResponse
import tjro.service.UrlService
import java.net.URI

@RestController
@CrossOrigin(origins = ["http://172.22.11.99:4200"])
@SecurityRequirement(name = "bearer-key")
class UrlController(private val urlService: UrlService) {

    @PostMapping("/encurta")
    fun encurta(@RequestBody request: UrlRequest): UrlResponse {
        val hash = urlService.encurtar(request.url)
        return UrlResponse(hash,"http://localhost:8080/$hash")
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