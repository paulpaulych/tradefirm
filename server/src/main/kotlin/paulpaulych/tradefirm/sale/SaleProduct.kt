package paulpaulych.tradefirm.sale

import paulpaulych.utils.Open

@Open
data class SaleProduct(
        val id: Long? = null,
        val saleId: Long,
        val productId: Long,
        val count: Long
)