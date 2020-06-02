package paulpaulych.tradefirm.application

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.tradefirm.security.MyGraphQLContext
import paulpaulych.tradefirm.security.SellerUser
import paulpaulych.tradefirm.seller.Seller
import simpleorm.core.findById
import simpleorm.core.save
import java.util.*

data class ApplicationItemInput(
        val productId: Long,
        val count: Int
)

@Component
class ApplicationMutation: Mutation{

    @Authorization("ROLE_USER")
    @Transactional
    fun createApplication(items: List<ApplicationItemInput>, context: MyGraphQLContext): Application{
        val application = Application(salesPoint = getSalesPoint(context), date = Date())
        val savedApplication = save(application)

        items.forEach{ (productId, count) ->
            val product = Product::class.findById(productId)
                    ?: error("product $productId not found")
            save(
                    ApplicationItem(
                            application = savedApplication,
                            product = product,
                            count = count)
            )
        }
        return savedApplication
    }

    private fun getSalesPoint(context: MyGraphQLContext): SalesPoint {
        val sellerUser = context.securityContext!!.authentication.principal as SellerUser
        val seller = Seller::class.findById(sellerUser.sellerId)
                ?: error("seller not found")
        return seller.salesPoint
    }

}