package paulpaulych.tradefirm.product

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.config.security.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.findAll

@Component
class ProductQuery: Query {

    private val log by LoggerDelegate()

    @Authorization("ROLE_USER")
    suspend fun products(): List<Product>{
        return Product::class.findAll()
    }
}
