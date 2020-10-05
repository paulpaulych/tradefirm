package paulpaulych.tradefirm.sellerapi.salespoint

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.security.authorization.Authorization
import paulpaulych.tradefirm.security.authorization.SecurityGraphQLContext
import paulpaulych.tradefirm.sellerapi.SellerUser
import simpleorm.core.findById

@Component
class SalesPointQuery : Query {

    @Authorization("ROLE_SELLER")
    fun salesPoint(context: SecurityGraphQLContext): SalesPoint {
        val auth = context.securityContext
                ?.authentication
                ?: error("context is null")
        val user = auth.principal as SellerUser
        val seller = Seller::class.findById(user.sellerId)
                ?: error("seller info not found for current user")
        return seller.salesPoint
    }
}