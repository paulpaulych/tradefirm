package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import simpleorm.core.findAll
import simpleorm.core.findById

@Component
class SalesPointQuery: Query{

    fun salesPoint(id: Long): SalesPoint{
        return SalesPoint::class.findById(id)
                ?: error("sales point not found")
    }

    fun salesPoints(): List<SalesPoint>{
        return SalesPoint::class.findAll()
    }
}