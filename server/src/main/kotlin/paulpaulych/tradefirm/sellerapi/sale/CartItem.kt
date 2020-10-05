package paulpaulych.tradefirm.sellerapi.sale

import paulpaulych.tradefirm.sellerapi.product.Product
import paulpaulych.utils.Open

@Open
data class CartItem(
        val id: Long? = null,
        val saleId: Long,
        val product: Product,
        val count: Long
)