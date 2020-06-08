package paulpaulych.tradefirm.product

import paulpaulych.tradefirm.apicore.PageInfo

data class ProductPage (
    val values: List<Product>,
    val pageInfo: PageInfo
)



