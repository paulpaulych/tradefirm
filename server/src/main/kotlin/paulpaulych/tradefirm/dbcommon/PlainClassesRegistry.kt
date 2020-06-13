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
            "customer" to PlainCustomer::class,
            "delivery" to PlainDelivery::class
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

data class PlainDelivery (
        val id: Long? = null,
        val orderId: Long,
        val supplierId: Long,
        val date: Date
)