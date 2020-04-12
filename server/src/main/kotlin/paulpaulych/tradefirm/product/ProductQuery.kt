package paulpaulych.tradefirm.product

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import simpleorm.core.findAll
import simpleorm.core.findById

@Component
class ProductQuery: Query{

    fun products(): List<Product>{
        return Product::class.findAll()
    }

    fun product(id: Long): Product?{
        return Product::class.findById(id)
    }

}
