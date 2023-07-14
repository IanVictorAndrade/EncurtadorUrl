package tjro.repositorio

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import tjro.entidade.Url
import java.util.*


@Repository
interface UrlRepository: CrudRepository<Url, Long> {


    fun findByHash(valor: String): Url?


}