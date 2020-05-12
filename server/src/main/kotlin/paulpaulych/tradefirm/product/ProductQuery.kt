package paulpaulych.tradefirm.product

import com.expediagroup.graphql.spring.operations.Query
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Mono
import simpleorm.core.findAll
import simpleorm.core.findById

@Component
class ProductQuery: Query{

    private val log by LoggerDelegate()

    suspend fun products(): List<Product>{
       return Product::class.findAll()
    }

    suspend fun product(id: Long): Product{
        return Product::class.findById(id)
                ?: error("data not found")
    }

}
