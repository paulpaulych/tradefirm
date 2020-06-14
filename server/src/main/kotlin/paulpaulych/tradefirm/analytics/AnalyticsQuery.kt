package paulpaulych.tradefirm.analytics

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.annotations.GraphQLName
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.config.graphql.expectedError
import paulpaulych.tradefirm.salespoint.Customer
import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.delivery.Supplier
import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.tradefirm.salespoint.Seller
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

    @GraphQLDescription("Поставщики которые поставляли определенный продукт хотя бы раз")
    fun suppliersByProduct(productId: Long): List<Supplier>{
        checkProductExistence(productId)
        val sql = ResourceLoader.loadText("sql/analytics/suppliers/1.sql")
        return Supplier::class.query(sql, listOf(productId))
    }

    @GraphQLDescription("Поставщики которые поставили заданный торав в суммарном объеме больше заданного")
    fun suppliersByProductAndVolume(productId: Long, volume: Int): List<SupplierInfo>{
        checkProductExistence(productId)
        val sql = ResourceLoader.loadText("sql/analytics/suppliers/2.sql")
        return SupplierInfo::class.query(sql, listOf(productId, volume))
    }

    @GraphQLDescription("Покупатели, хотя бы раз покупавшие заданный товар")
    fun customersByProduct(productId: Long): List<Customer>{
        checkProductExistence(productId)
        val sql = ResourceLoader.loadText("sql/analytics/customers/1.sql")
        return Customer::class.query(sql, listOf(productId))
    }

    @GraphQLDescription("Покупатели которые купили заданный товар в суммарном объеме не менее заданного")
    fun customersByProductAndVolume(productId: Long, volume: Int): List<CustomerInfo>{
        checkProductExistence(productId)
        val sql = ResourceLoader.loadText("sql/analytics/customers/2.sql")
        return CustomerInfo::class.query(sql, listOf(productId, volume))
    }

    @GraphQLDescription("данные по выработке на одного продавца")
    fun productionBySeller(): ProductionBySeller {
        val sql = ResourceLoader.loadText("sql/analytics/seller/1.sql")
        val result = jdbc.queryForObject(sql, BigDecimal::class.java)!!
        return ProductionBySeller(result)
    }

    @GraphQLDescription("данные по выработке на одного продавца")
    fun productionByGivenSeller(sellerId: Long): ProductionBySeller {
        checkSellerExistence(sellerId)
        val sql = ResourceLoader.loadText("sql/analytics/seller/2.sql")
        val result = jdbc.queryForObject(sql, arrayOf(sellerId), BigDecimal::class.java)!!
        return ProductionBySeller(result)
    }

    @GraphQLDescription("Кол-во единиц проданного товара по контретной торговой точке")
    fun productSoldBySalesPoint(productId: Long, salesPointId: Long): ProductSold {
        checkProductExistence(productId)
        checkSalesPointExistence(salesPointId)
        val sql = ResourceLoader.loadText("sql/analytics/product/soldBySalesPoint.sql")
        val result = jdbc.queryForObject(sql, arrayOf(productId, salesPointId), Long::class.java)!!
        return ProductSold(result)
    }

    @GraphQLDescription("Зарплаты продавцой данной торговой точки")
    fun sellerSalaryBySalesPoint(salesPointId: Long): List<SellerSalary> {
        checkSalesPointExistence(salesPointId)
        val sql = ResourceLoader.loadText("sql/analytics/seller/salaryBySalesPoint.sql")
        return SellerSalary::class.query(sql, listOf(salesPointId))
    }

    @GraphQLDescription("поставки определенного товара указанным поставщиком")
    fun deliveryByProductAndSupplier(productId: Long, supplierId: Long): List<DeliveryInfo> {
        checkProductExistence(productId)
        checkSupplierExistence(supplierId)
        val sql = ResourceLoader.loadText("sql/analytics/product/suppliedBySupplier.sql")
        return DeliveryInfo::class.query(sql, listOf(productId, supplierId))
    }

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
    /**
     * throws error if product does not exist
     */
    private fun checkProductExistence(productId: Long){
        if(Product::class.findById(productId) == null){
            expectedError("продукт $productId не существует" )
        }
        return
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
            expectedError("продавец $supplierId не найден")
        }
    }

}

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