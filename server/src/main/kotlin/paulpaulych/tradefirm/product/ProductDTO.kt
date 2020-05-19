package paulpaulych.tradefirm.product

import paulpaulych.tradefirm.apicore.PageInfo

data class ProductDTO (
    val values: List<Product>,
    val pageInfo: PageInfo
)



