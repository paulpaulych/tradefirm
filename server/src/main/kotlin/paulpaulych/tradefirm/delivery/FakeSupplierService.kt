package paulpaulych.tradefirm.delivery

import org.springframework.stereotype.Service
import paulpaulych.tradefirm.order.SupplierOrder
import paulpaulych.tradefirm.supplier.Supplier
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.findAll
import simpleorm.core.save
import java.util.*

@Service
class FakeSupplierService: SupplierService {

    override fun applyOrder(supplierOrder: SupplierOrder) {
        val suppliers = Supplier::class.findAll()

        val delivery = Delivery(
                supplierOrder = supplierOrder,
                supplier = suppliers.random(),
                date = Date())
        val savedDelivery = save(delivery)
        log.info("delivery created: $savedDelivery")
    }

    private val log by LoggerDelegate()

}