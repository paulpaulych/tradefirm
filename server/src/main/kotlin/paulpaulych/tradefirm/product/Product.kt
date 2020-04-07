package paulpaulych.tradefirm.product

import paulpaulych.utils.Open

@Open
data class Product(
        val name: String,
        val id: Long? = null
)