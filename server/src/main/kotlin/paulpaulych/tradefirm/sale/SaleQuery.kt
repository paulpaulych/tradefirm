package paulpaulych.tradefirm.sale

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.security.MyGraphQLContext
import simpleorm.core.findAll
import simpleorm.core.findById
import simpleorm.core.query

@Component
class SaleQuery : Query {

    suspend fun sale(id: Long): Sale{
        return Sale::class.findById(id)
                ?: error("sale not found")
    }

    suspend fun sales(): List<Sale>{
        return Sale::class.findAll()
    }


}

