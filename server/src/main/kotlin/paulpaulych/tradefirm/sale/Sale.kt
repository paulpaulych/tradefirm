package paulpaulych.tradefirm.sale

import com.expediagroup.graphql.annotations.GraphQLIgnore
import paulpaulych.tradefirm.customer.Customer
import paulpaulych.tradefirm.employee.Employee
import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.utils.Open
import java.util.*

@Open
data class Sale (
        val id: Long? = null,
        val customer: Customer,
        val salesPoint: SalesPoint,
        val seller: Employee?,
        @GraphQLIgnore
        val date: Date,
        val cartItems: List<SaleProduct> = listOf()
)

