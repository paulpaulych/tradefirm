package paulpaulych.tradefirm.application

import paulpaulych.tradefirm.product.Product
import paulpaulych.utils.Open

@Open
data class ApplicationItem(
        val id: Long? = null,
        val application: Application,
        val product: Product,
        val count: Int
)