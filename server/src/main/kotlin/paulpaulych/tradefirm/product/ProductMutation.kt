package paulpaulych.tradefirm.product

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.save

@Component
class ProductMutation: Mutation {

    private val log by LoggerDelegate()

    fun saveProduct(product: Product): Product{
        log.info("saving products")
        return save(product)
    }

}
