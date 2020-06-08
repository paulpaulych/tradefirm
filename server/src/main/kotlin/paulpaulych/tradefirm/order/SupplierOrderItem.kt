package paulpaulych.tradefirm.order

import paulpaulych.tradefirm.product.Product
import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.utils.Open

@Open
data class SupplierOrderItem(
        val id: Long? = null,
        val supplierOrder: SupplierOrder,
        val product: Product,
        val salesPoint: SalesPoint,
        val count: Int
)