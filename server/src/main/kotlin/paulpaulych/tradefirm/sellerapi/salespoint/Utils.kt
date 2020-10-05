package paulpaulych.tradefirm.sellerapi.salespoint

import paulpaulych.tradefirm.security.authorization.SecurityGraphQLContext
import paulpaulych.tradefirm.sellerapi.SellerUser
import simpleorm.core.findById

fun getSalesPoint(context: SecurityGraphQLContext): SalesPoint {
    val sellerUser = context.securityContext!!.authentication.principal as SellerUser
    val seller = Seller::class.findById(sellerUser.sellerId)
            ?: error("seller not found")
    return seller.salesPoint
}