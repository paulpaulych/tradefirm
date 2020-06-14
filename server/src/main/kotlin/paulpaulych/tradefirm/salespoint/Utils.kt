package paulpaulych.tradefirm.salespoint

import paulpaulych.tradefirm.config.security.MyGraphQLContext
import paulpaulych.tradefirm.config.security.SellerUser
import simpleorm.core.findById

fun getSalesPoint(context: MyGraphQLContext): SalesPoint {
    val sellerUser = context.securityContext!!.authentication.principal as SellerUser
    val seller = Seller::class.findById(sellerUser.sellerId)
            ?: error("seller not found")
    return seller.salesPoint
}