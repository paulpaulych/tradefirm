package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.area.Area
import simpleorm.core.findById
import simpleorm.core.save

@Component
class SalePointMutation: Mutation {

    //TODO: make transactional
    //TODO: do in batch update
    suspend fun saveSalesPoints(values: List<SalesPointSaveReq>): List<SalesPoint> {
        return values.map {
            var area: Area? = null
            if (it.areaId != null) {
                area = Area::class.findById(it.areaId)
                        ?: error("area not found with id = ${it.areaId}")
            }
            save(SalesPoint(it.id, it.type, area))
        }
    }

}

data class SalesPointSaveReq(
        val id: Long? = null,
        val type: String,
        val areaId: Long? = null
)