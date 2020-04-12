package paulpaulych.tradefirm.product

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import simpleorm.core.save

@Component
class ProductMutation: Mutation {

    fun saveProduct(product: Product): Product{
        return save(product)
    }

}
