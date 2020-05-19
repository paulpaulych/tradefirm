package paulpaulych.tradefirm.salespoint

import paulpaulych.tradefirm.apicore.PageInfo

data class SalesPointDTO (
    val values: List<SalesPoint>,
    val pageInfo: PageInfo
)
