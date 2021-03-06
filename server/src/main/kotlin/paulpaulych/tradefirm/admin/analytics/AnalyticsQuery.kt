package paulpaulych.tradefirm.admin.analytics

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.annotations.GraphQLName
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.config.graphql.expectedError
import paulpaulych.tradefirm.security.authorization.Authorization
import paulpaulych.tradefirm.sellerapi.salespoint.Customer
import paulpaulych.tradefirm.sellerapi.product.Product
import paulpaulych.tradefirm.sellerapi.delivery.Supplier
import paulpaulych.tradefirm.sellerapi.salespoint.SalesPoint
import paulpaulych.tradefirm.sellerapi.salespoint.Seller
import paulpaulych.tradefirm.admin.crudapi.CrudOrders
import paulpaulych.utils.ResourceLoader
import simpleorm.core.findById
import simpleorm.core.query
import java.math.BigDecimal

@Component
@GraphQLName("Analytics")
@Transactional(isolation = Isolation.REPEATABLE_READ)
class AnalyticsQuery(
        private val jdbc: JdbcTemplate
): Query {

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("Поставщики которые поставляли определенный продукт хотя бы раз")
    fun suppliersByProduct(productId: Long): List<Supplier>{
        checkProductExistence(productId)
        val sql = ResourceLoader.loadText("sql/analytics/suppliers/1.sql")
        return Supplier::class.query(sql, listOf(productId))
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("Поставщики которые поставили заданный торав в суммарном объеме больше заданного")
    fun suppliersByProductAndVolume(productId: Long, volume: Int): List<SupplierInfo>{
        checkProductExistence(productId)
        val sql = ResourceLoader.loadText("sql/analytics/suppliers/2.sql")
        return SupplierInfo::class.query(sql, listOf(productId, volume))
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("Покупатели, хотя бы раз покупавшие заданный товар")
    fun customersByProduct(productId: Long): List<Customer>{
        checkProductExistence(productId)
        val sql = ResourceLoader.loadText("sql/analytics/customers/1.sql")
        return Customer::class.query(sql, listOf(productId))
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("Покупатели которые купили заданный товар в суммарном объеме не менее заданного")
    fun customersByProductAndVolume(productId: Long, volume: Int): List<CustomerInfo>{
        checkProductExistence(productId)
        val sql = ResourceLoader.loadText("sql/analytics/customers/2.sql")
        return CustomerInfo::class.query(sql, listOf(productId, volume))
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("данные по выработке на одного продавца")
    fun productionBySeller(): ProductionBySeller {
        val sql = ResourceLoader.loadText("sql/analytics/seller/1.sql")
        val result = jdbc.queryForObject(sql, BigDecimal::class.java)!!
        return ProductionBySeller(result)
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("данные по выработке на одного продавца")
    fun productionByGivenSeller(sellerId: Long): ProductionBySeller {
        checkSellerExistence(sellerId)
        val sql = ResourceLoader.loadText("sql/analytics/seller/2.sql")
        val result = jdbc.queryForObject(sql, arrayOf(sellerId), BigDecimal::class.java)!!
        return ProductionBySeller(result)
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("Кол-во единиц проданного товара по контретной торговой точке")
    fun productSoldBySalesPoint(productId: Long, salesPointId: Long): ProductSold {
        checkProductExistence(productId)
        checkSalesPointExistence(salesPointId)
        val sql = ResourceLoader.loadText("sql/analytics/product/soldBySalesPoint.sql")
        val result = jdbc.queryForObject(sql, arrayOf(productId, salesPointId), Long::class.java)!!
        return ProductSold(result)
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("Зарплаты продавцой данной торговой точки")
    fun sellerSalaryBySalesPoint(salesPointId: Long): List<SellerSalary> {
        checkSalesPointExistence(salesPointId)
        val sql = ResourceLoader.loadText("sql/analytics/seller/salaryBySalesPoint.sql")
        return SellerSalary::class.query(sql, listOf(salesPointId))
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("поставки определенного товара указанным поставщиком")
    fun deliveryByProductAndSupplier(productId: Long, supplierId: Long): List<DeliveryInfo> {
        checkProductExistence(productId)
        checkSupplierExistence(supplierId)
        val sql = ResourceLoader.loadText("sql/analytics/product/suppliedBySupplier.sql")
        return DeliveryInfo::class.query(sql, listOf(productId, supplierId))
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("Отношение объема продаж к объему торг площадей")
    fun productionToSquare(): List<ProductionToSquareRatio>{
        val sql = ResourceLoader.loadText("sql/analytics/salespoint/productionToSquare.sql")
        return jdbc.query(sql){ rs, _ ->
            ProductionToSquareRatio(
                    rs.getLong("sales_point_id"),
                    rs.getBigDecimal("ratio")
            )
        }
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("рентабельность торговых точек(отношение объема продаж к накладным расходам)")
    fun profitability(): List<Profitability>{
        val sql = ResourceLoader.loadText("sql/analytics/salespoint/profitability.sql")
        return jdbc.query(sql){ rs, _ ->
            Profitability(
                    rs.getLong("sales_point_id"),
                    rs.getBigDecimal("profitability")
            )
        }
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("Сведения о поставках по номеру заказа")
    fun deliveriesByOrder(orderId: Long): List<Long>{
        checkOrderExistence(orderId)
        val sql = ResourceLoader.loadText("sql/analytics/delivery/byOrder.sql")
        return jdbc.queryForList(sql, arrayOf(orderId), Long::class.java)
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("Покупатели, которые покупали данный товар")
    fun customerByProduct(productId: Long): List<Long>{
        checkProductExistence(productId)
        val sql = ResourceLoader.loadText("sql/analytics/product/customerByProduct.sql")
        return jdbc.queryForList(sql, arrayOf(productId), Long::class.java)
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("Топ покупателей по количеству покупок")
    fun mostActiveCustomers(): List<CustomerSalesCount>{
        val sql = ResourceLoader.loadText("sql/analytics/customers/mostActive.sql")
        return jdbc.query(sql){ rs, _ ->
            CustomerSalesCount(
                    rs.getLong("customer_id"),
                    rs.getLong("sales_count")
            )
        }
    }

    @Authorization("ROLE_ADMIN")
    @GraphQLDescription("Данные о товарообороте данной торговой точки")
    fun productStatBySalesPoint(salesPointId: Long): List<ProductStat> {
        checkSalesPointExistence(salesPointId)
        val sql = ResourceLoader.loadText("sql/analytics/salespoint/totalProductsSold.sql")
        return ProductStat::class.query(sql, listOf(salesPointId))
    }

    /**
     * throws error if product does not exist
     */
    private fun checkProductExistence(productId: Long){
        if(Product::class.findById(productId) == null){
            expectedError("продукт $productId не существует" )
        }
    }

    private fun checkSalesPointExistence(salesPointId: Long){
        if(SalesPoint::class.findById(salesPointId) == null){
            expectedError("точка продаж $salesPointId не найдена")
        }
    }

    private fun checkSellerExistence(sellerId: Long){
        if(Seller::class.findById(sellerId) == null){
            expectedError("продавец $sellerId не найден")
        }
    }

    private fun checkSupplierExistence(supplierId: Long){
        if(Supplier::class.findById(supplierId) == null){
            expectedError("поставщик $supplierId не найден")
        }
    }

    private fun checkOrderExistence(id: Long){
        if(CrudOrders::class.findById(id) == null){
            expectedError("заказ $id не найден")
        }
    }
}

data class ProductStat(
    val id: Long,
    val totallySold: Long
)

data class CustomerSalesCount(
        val customerId: Long,
        val salesCount: Long
)

data class Profitability(
        val salesPointId: Long,
        val ratio: BigDecimal
)

data class ProductionToSquareRatio(
        val salesPointId: Long,
        val ratio: BigDecimal
)

data class ProductSold(
        val totallySold: Long
)

data class ProductionBySeller(
        val value: BigDecimal
)

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

data class SellerSalary(
        val id: Long,
        val salary: BigDecimal
)

data class DeliveryInfo(
        val id: Long,
        val count: Long
)