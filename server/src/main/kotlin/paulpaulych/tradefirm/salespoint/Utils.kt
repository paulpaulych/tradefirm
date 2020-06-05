package paulpaulych.tradefirm.salespoint

import paulpaulych.tradefirm.security.MyGraphQLContext
import paulpaulych.tradefirm.security.SellerUser
import paulpaulych.tradefirm.seller.Seller
import simpleorm.core.findById

fun getSalesPoint(context: MyGraphQLContext): SalesPoint {
    val sellerUser = context.securityContext!!.authentication.principal as SellerUser
    val seller = Seller::class.findById(sellerUser.sellerId)
            ?: error("seller not found")
    return seller.salesPoint
}