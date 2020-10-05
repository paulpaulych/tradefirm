package paulpaulych.tradefirm.sellerapi.order

import paulpaulych.tradefirm.sellerapi.product.Product
import paulpaulych.tradefirm.sellerapi.salespoint.SalesPoint
import paulpaulych.utils.Open

@Open
data class SupplierOrderItem(
        val id: Long? = null,
        val supplierOrder: SupplierOrder,
        val product: Product,
        val salesPoint: SalesPoint,
        val count: Int
)