package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.tradefirm.security.MyGraphQLContext
import paulpaulych.tradefirm.security.SellerUser
import paulpaulych.tradefirm.seller.Seller
import simpleorm.core.findById

@Component
class SalesPointQuery : Query {

    @Authorization("ROLE_USER")
    fun salesPoint(context: MyGraphQLContext): SalesPoint{
        val auth = context.securityContext
                ?.authentication
                ?: error("context is null")
        val user = auth.principal as SellerUser
        val seller = Seller::class.findById(user.sellerId)
                ?: error("seller info not found for current user")
        return seller.salesPoint
    }
}