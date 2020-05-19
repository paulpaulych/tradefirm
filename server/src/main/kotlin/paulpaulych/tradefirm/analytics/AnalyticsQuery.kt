package paulpaulych.tradefirm.analytics

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.annotations.GraphQLName
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.customer.Customer
import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.supplier.Supplier
import paulpaulych.utils.ResourceLoader
import simpleorm.core.findById
import simpleorm.core.query

@Component
@GraphQLName("Analytics")
@Transactional(isolation = Isolation.REPEATABLE_READ)
class AnalyticsQuery: Query {

    @GraphQLDescription("Поставщики которые поставляли определенный продукт хотя бы раз")
    fun suppliersByProduct(productId: Long): List<Supplier>{
        checkProductExistance(productId)
        val sql = ResourceLoader.loadText("sql/analytics/suppliers/1.sql")
        return Supplier::class.query(sql, listOf(productId))
    }

    @GraphQLDescription("Поставщики которые поставили заданный торав в суммарном объеме больше заданного")
    fun suppliersByProductAndVolume(productId: Long, volume: Int): List<SupplierInfo>{
        checkProductExistance(productId)
        val sql = ResourceLoader.loadText("sql/analytics/suppliers/2.sql")
        return SupplierInfo::class.query(sql, listOf(productId, volume))
    }

    @GraphQLDescription("Покупатели, хотя бы раз покупавшие заданный товар")
    fun customersByProduct(productId: Long): List<Customer>{
        checkProductExistance(productId)
        val sql = ResourceLoader.loadText("sql/analytics/customers/1.sql")
        return Customer::class.query(sql, listOf(productId))
    }

    @GraphQLDescription("Покупатели, хотя бы раз покупавшие заданный товар")
    fun customersByProductAndVolume(productId: Long, volume: Int): List<CustomerInfo>{
        checkProductExistance(productId)
        val sql = ResourceLoader.loadText("sql/analytics/customers/2.sql")
        return CustomerInfo::class.query(sql, listOf(productId, volume))
    }

    /**
     * throws error if product does not exist
     */
    private fun checkProductExistance(productId: Long){
        if(Product::class.findById(productId) == null){
            error("product with id = $productId does not exists" )
        }
        return
    }

}

data class SupplierInfo(
        val id: Long,
        val companyName: String,
        val totallySupplied: Int
)

data class CustomerInfo(
        val id: Long,
        val name: String,
        val totallyBought: Int
)