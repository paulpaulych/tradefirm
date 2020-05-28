package paulpaulych.tradefirm.salespoint

import paulpaulych.tradefirm.apicore.PageInfo
import paulpaulych.tradefirm.salespoint.j.PlainSalesPoint

data class SalesPointDTO (
        val values: List<PlainSalesPoint>,
        val pageInfo: PageInfo
)
