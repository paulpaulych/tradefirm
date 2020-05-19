package paulpaulych.tradefirm.sale

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import simpleorm.core.findAll
import simpleorm.core.findById

@Component
class SaleQuery : Query {

    fun sale(id: Long): Sale{
        return Sale::class.findById(id)
                ?: error("sale not found")
    }

    fun sales(): List<Sale>{
        return Sale::class.findAll()
    }

}
