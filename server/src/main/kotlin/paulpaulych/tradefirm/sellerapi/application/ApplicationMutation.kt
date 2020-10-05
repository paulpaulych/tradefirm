package paulpaulych.tradefirm.sellerapi.application

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.sellerapi.product.Product
import paulpaulych.tradefirm.sellerapi.salespoint.getSalesPoint
import paulpaulych.tradefirm.security.authorization.Authorization
import paulpaulych.tradefirm.security.authorization.SecurityGraphQLContext
import simpleorm.core.findById
import simpleorm.core.persist
import java.util.*

data class ApplicationItemInput(
        val productId: Long,
        val count: Int
)

@Component
class ApplicationMutation: Mutation{

    @Authorization("ROLE_SELLER")
    @Transactional
    fun createApplication(items: List<ApplicationItemInput>, context: SecurityGraphQLContext): Application {
        val application = Application(salesPoint = getSalesPoint(context), date = Date())
        val savedApplication = persist(application)

        items.forEach { (productId, count) ->
            val product = Product::class.findById(productId)
                    ?: error("product $productId not found")
            persist(ApplicationItem(application = savedApplication,
                    product = product,
                    count = count))
        }
        return savedApplication
    }

}