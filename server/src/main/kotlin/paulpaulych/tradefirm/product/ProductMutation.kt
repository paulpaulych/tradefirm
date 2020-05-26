package paulpaulych.tradefirm.product

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.delete
import simpleorm.core.save

@Component
class ProductMutation: Mutation {

    private val log by LoggerDelegate()

    suspend fun saveProducts(values: List<Product>): List<Product>{
        log.info("saving products")
        return values.map{save(it)}
    }

    suspend fun deleteProducts(values: List<Long>): List<Long>{
        values.forEach{ Product::class.delete (it) }
        return values
    }

}
