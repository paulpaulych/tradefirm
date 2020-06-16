package paulpaulych.tradefirm.delivery

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import paulpaulych.tradefirm.order.SupplierOrder
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.filter.EqFilter
import simpleorm.core.findAll
import simpleorm.core.findBy
import simpleorm.core.findById
import simpleorm.core.pagination.PageRequest
import simpleorm.core.pagination.Sort
import simpleorm.core.persist
import java.util.*

@Service
class FakeSupplierService {

    @Scheduled(fixedRateString = "\${order.create.fixedRateMillis}", initialDelay = 3000)
    fun handleOrders() {
        val suppliers = Supplier::class.findAll()
        fetchNewWfw().forEach {wfw ->
            val order = SupplierOrder::class.findById(wfw.orderId)
                    ?: error("order not found")
            val delivery = Delivery(
                    supplierOrder = order,
                    supplier = suppliers.random(),
                    date = Date())
            val savedDelivery = persist(delivery)
            log.info("delivery created: $savedDelivery")
            updateWfw(wfw)
        }
    }

    private fun updateWfw(wfw: DeliveryWfw) {
        persist(wfw.copy(is_new = null))
    }

    private fun fetchNewWfw(): List<DeliveryWfw>{
        return DeliveryWfw::class.findBy(
                EqFilter(DeliveryWfw::is_new, true),
                PageRequest(0, 1,
                        listOf(Sort(DeliveryWfw::id, Sort.Order.ASC))
                )
        ).values
    }
    private val log by LoggerDelegate()

}

data class DeliveryWfw(
        val id: Long? = null,
        val orderId: Long,
        val is_new: Boolean?
)