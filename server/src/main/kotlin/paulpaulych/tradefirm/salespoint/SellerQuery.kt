package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.salespoint.Seller
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import simpleorm.core.findAll
import simpleorm.core.findById

@Component
class SellerQuery: Query {

    private val log by LoggerDelegate()

    suspend fun seller(id: Long): Seller {
        log.info("seller requested")
        return Seller::class.findById(id)
                ?: error("data not found")
    }

    fun sellers(): Flux<Seller> {
        return Seller::class.findAll().toFlux()
    }

}