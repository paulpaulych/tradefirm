package paulpaulych.tradefirm.delivery

import paulpaulych.tradefirm.order.SupplierOrder

interface SupplierService{
    fun applyOrder(supplierOrder: SupplierOrder)
}