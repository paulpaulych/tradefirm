package paulpaulych.tradefirm.salespoint

import paulpaulych.tradefirm.api.PageInfo

data class SalesPointDTO (
    val values: List<SalesPoint>,
    val pageInfo: PageInfo
)
