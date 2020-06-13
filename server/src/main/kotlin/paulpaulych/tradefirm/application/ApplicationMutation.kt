package paulpaulych.tradefirm.application

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.salespoint.getSalesPoint
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.tradefirm.security.MyGraphQLContext
import simpleorm.core.batchInsert
import simpleorm.core.findById
import simpleorm.core.persist
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
        val savedApplication = persist(application)

        val applicationItems = items.map { (productId, count) ->
            val product = Product::class.findById(productId)
                    ?: error("product $productId not found")
            ApplicationItem(application = savedApplication,
                            product = product,
                            count = count)
        }
        batchInsert(applicationItems)
        return savedApplication
    }

}