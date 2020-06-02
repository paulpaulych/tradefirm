package paulpaulych.tradefirm.sale

import paulpaulych.tradefirm.product.Product
import paulpaulych.utils.Open

@Open
data class CartItem(
        val id: Long? = null,
        val saleId: Long,
        val product: Product,
        val count: Long
)