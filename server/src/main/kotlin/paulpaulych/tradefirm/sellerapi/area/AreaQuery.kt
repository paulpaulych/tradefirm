package paulpaulych.tradefirm.sellerapi.area

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import simpleorm.core.findAll
import simpleorm.core.findById

@Component
class AreaQuery: Query {

    fun area(id: Long): Area {
        return Area::class.findById(id)
                ?: error("area not found")
    }

    fun areas(): List<Area>{
        return Area::class.findAll()
    }
}