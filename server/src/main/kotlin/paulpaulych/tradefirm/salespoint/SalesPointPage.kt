package paulpaulych.tradefirm.salespoint

import paulpaulych.tradefirm.apicore.PageInfo

data class SalesPointPage (
        val values: List<PlainSalesPoint>,
        val pageInfo: PageInfo
)
