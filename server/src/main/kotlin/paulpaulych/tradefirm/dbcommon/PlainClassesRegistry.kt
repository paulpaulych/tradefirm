package paulpaulych.tradefirm.dbcommon

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*
import kotlin.reflect.KClass

@Component
class PlainClassesRegistry{
    private val types = mapOf(
            "product" to PlainProduct::class,
            "seller" to PlainSeller::class,
            "area" to PlainArea::class,
            "salesPoint" to PlainSalesPoint::class,
            "sale" to PlainSale::class,
            "sale_product" to PlainSaleProduct::class,
            "customer" to PlainCustomer::class,
            "delivery" to PlainDelivery::class,
            "application" to PlainApplication::class,
            "application_product" to PlainApplicationProduct::class,
            "order" to PlainOrders::class,
            "order_product" to PlainOrderProduct::class,
            "supplier" to PlainSupplier::class,
            "shop_delivery" to PlainShopDelivery::class,
            "shop_delivery_product" to PlainShopDeliveryItems::class,
            "storage" to PlainStorage::class,
            "supplier_price" to PlainSupplierPriceList::class
    )

    @Suppress("UNCHECKED_CAST")
    fun getByTypeName(type: String): KClass<Any> {
        val kClass = types[type]
                ?: error("type $type not found in plain classes registry")
        return kClass as KClass<Any>
    }
}

data class PlainProduct(
        val id: Long? = null,
        val name: String
)

data class PlainArea(
        val id: Long? = null,
        val square: Long,
        val rentPrice: BigDecimal,
        val municipalServicesPrice: BigDecimal,
        val stallCount: Int
)

data class PlainSeller(
        val id: Long? = null,
        val name: String,
        val salesPointId: Long,
        val salary: BigDecimal
)

data class PlainSalesPoint(
        val id: Long? = null,
        val type: String,
        val areaId: Long? = null
)

data class PlainSale(
        val id: Long? = null,
        val customerId: Long,
        val salesPointId: Long,
        val sellerId: Long,
        val date: Date
)

data class PlainCustomer(
        val id: Long? = null,
        val name: String
)

data class PlainSupplier(
        val id: Long? = null,
        val companyName: String
)

data class PlainDelivery (
        val id: Long? = null,
        val orderId: Long,
        val supplierId: Long,
        val date: Date
)

data class PlainApplication(
        val id: Long? = null,
        val salesPointId: Long,
        val newFlag: Boolean,
        val date: Date
)

data class PlainApplicationProduct(
        val id: Long? = null,
        val applicationId: Long,
        val productId: Long,
        val count: Int
)

data class PlainOrders(
        val id: Long? = null,
        val date: Date
)

data class PlainOrderProduct(
        val id: Long? = null,
        val salesPointId: Long,
        val productId: Long,
        val orderId: Long,
        val count: Int
)

data class PlainSaleProduct(
        val id: Long? = null,
        val saleId: Long,
        val productId: Long,
        val count: Int
)

data class PlainShopDelivery(
        val id: Long? = null,
        val deliveryId: Long,
        val salesPointId: Long,
        val date: Date
)

data class PlainShopDeliveryItems(
        val id: Long? = null,
        val shopDeliveryId: Long,
        val productId: Long,
        val count: Int
)

data class PlainStorage(
        val id: Long? = null,
        val productId: Long,
        val salesPointId: Long,
        val count: Int,
        val price: BigDecimal
)

data class PlainSupplierPriceList(
        val id: Long? = null,
        val supplierId: Long,
        val productId: Long,
        val price: BigDecimal
)