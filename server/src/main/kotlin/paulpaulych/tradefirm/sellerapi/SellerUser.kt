package paulpaulych.tradefirm.sellerapi

import paulpaulych.tradefirm.security.Role
import paulpaulych.tradefirm.security.User

class SellerUser(
        username: String,
        password: String,
        enabled: Boolean,
        roles: List<Role>,
        val sellerId: Long
): User(username, password, enabled, roles)