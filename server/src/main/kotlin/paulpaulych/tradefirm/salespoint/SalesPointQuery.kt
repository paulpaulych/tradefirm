package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.config.security.common.Authorization
import paulpaulych.tradefirm.config.security.common.MyGraphQLContext
import paulpaulych.tradefirm.config.security.common.SellerUser
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