package paulpaulych.tradefirm.application

import com.expediagroup.graphql.spring.operations.Query
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

@Component
class ApplicationQuery: Query {

}