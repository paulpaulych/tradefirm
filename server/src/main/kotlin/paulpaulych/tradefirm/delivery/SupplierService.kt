package paulpaulych.tradefirm.delivery

import paulpaulych.tradefirm.order.Order

interface SupplierService{
    fun applyOrder(order: Order)
}