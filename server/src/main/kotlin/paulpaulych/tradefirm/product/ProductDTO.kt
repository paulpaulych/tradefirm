package paulpaulych.tradefirm.product

import paulpaulych.tradefirm.api.PageInfo

data class ProductDTO (
    val values: List<Product>,
    val pageInfo: PageInfo
)



