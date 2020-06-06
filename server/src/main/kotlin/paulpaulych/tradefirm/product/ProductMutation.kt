package paulpaulych.tradefirm.product

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.delete
import simpleorm.core.save

@Component
class ProductMutation: Mutation {

    private val log by LoggerDelegate()

    @Authorization("ROLE_ADMIN")
    suspend fun saveProducts(values: List<Product>): List<Product>{
        log.info("saving products")
        return values.map{save(it)}
    }

    @Authorization("ROLE_ADMIN")
    suspend fun deleteProducts(ids: List<Long>): List<Long>{
        ids.forEach{ Product::class.delete (it) }
        return ids
    }

}
