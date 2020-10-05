package paulpaulych.tradefirm.admin.crudapi

import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*
import kotlin.reflect.KClass

@Component
class CrudEntitiesRegistry{
    private val types = mapOf(
            "product" to CrudProduct::class,
            "seller" to CrudSeller::class,
            "area" to CrudArea::class,
            "salesPoint" to CrudSalesPoint::class,
            "sale" to CrudSale::class,
            "sale_product" to CrudSaleProduct::class,
            "customer" to CrudCustomer::class,
            "delivery" to CrudDelivery::class,
            "application" to CrudApplication::class,
            "application_product" to CrudApplicationProduct::class,
            "order" to CrudOrders::class,
            "order_product" to CrudOrderProduct::class,
            "supplier" to CrudSupplier::class,
            "shop_delivery" to CrudShopDelivery::class,
            "shop_delivery_product" to CrudShopDeliveryItems::class,
            "storage" to CrudStorage::class,
            "supplier_price" to CrudSupplierPriceList::class
    )

    @Suppress("UNCHECKED_CAST")
    fun getByTypeName(type: String): KClass<Any> {
        val kClass = types[type]
                ?: error("type $type not found in Crud classes registry")
        return kClass as KClass<Any>
    }
}

data class CrudProduct(
        val id: Long? = null,
        val name: String
)

data class CrudArea(
        val id: Long? = null,
        val square: Long,
        val rentPrice: BigDecimal,
        val municipalServicesPrice: BigDecimal,
        val stallCount: Int
)

data class CrudSeller(
        val id: Long? = null,
        val name: String,
        val salesPointId: Long,
        val salary: BigDecimal
)

data class CrudSalesPoint(
        val id: Long? = null,
        val type: String,
        val areaId: Long? = null
)

data class CrudSale(
        val id: Long? = null,
        val customerId: Long,
        val salesPointId: Long,
        val sellerId: Long,
        val date: Date
)

data class CrudCustomer(
        val id: Long? = null,
        val name: String
)

data class CrudSupplier(
        val id: Long? = null,
        val companyName: String
)

data class CrudDelivery (
        val id: Long? = null,
        val orderId: Long,
        val supplierId: Long,
        val date: Date
)

data class CrudApplication(
        val id: Long? = null,
        val salesPointId: Long,
        val newFlag: Boolean,
        val date: Date
)

data class CrudApplicationProduct(
        val id: Long? = null,
        val applicationId: Long,
        val productId: Long,
        val count: Int
)

data class CrudOrders(
        val id: Long? = null,
        val date: Date
)

data class CrudOrderProduct(
        val id: Long? = null,
        val salesPointId: Long,
        val productId: Long,
        val orderId: Long,
        val count: Int
)

data class CrudSaleProduct(
        val id: Long? = null,
        val saleId: Long,
        val productId: Long,
        val count: Int
)

data class CrudShopDelivery(
        val id: Long? = null,
        val deliveryId: Long,
        val salesPointId: Long,
        val date: Date
)

data class CrudShopDeliveryItems(
        val id: Long? = null,
        val shopDeliveryId: Long,
        val productId: Long,
        val count: Int
)

data class CrudStorage(
        val id: Long? = null,
        val productId: Long,
        val salesPointId: Long,
        val count: Int,
        val price: BigDecimal
)

data class CrudSupplierPriceList(
        val id: Long? = null,
        val supplierId: Long,
        val productId: Long,
        val price: BigDecimal
)