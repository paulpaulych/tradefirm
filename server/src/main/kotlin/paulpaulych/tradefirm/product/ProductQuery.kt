package paulpaulych.tradefirm.product

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.findAll
import simpleorm.core.findById

@Component
class ProductQuery: Query{

    private val log by LoggerDelegate()

    fun products(): List<Product>{
        log.info("products requested")
        return Product::class.findAll()
    }

    fun product(id: Long): Product?{
        return Product::class.findById(id)
    }

}
