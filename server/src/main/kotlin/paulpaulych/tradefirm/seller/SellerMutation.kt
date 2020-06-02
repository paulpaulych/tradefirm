package paulpaulych.tradefirm.seller

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.salespoint.SalesPoint
import simpleorm.core.findById
import simpleorm.core.save

@Component
class SellerMutation: Mutation{

    @Secured
    fun saveEmployee(seller: PlainSeller): Seller{

        val salesPoint = SalesPoint::class.findById(seller.salesPointId)
                ?: error("sales point with id = ${seller.salesPointId} not found")

        return save(Seller(
                id = seller.id,
                name = seller.name,
                salesPoint = salesPoint,
                salary = seller.salary
        ))

    }
}