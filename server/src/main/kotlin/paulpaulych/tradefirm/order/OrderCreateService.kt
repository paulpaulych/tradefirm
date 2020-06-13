package paulpaulych.tradefirm.order

import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import paulpaulych.tradefirm.application.Application
import paulpaulych.tradefirm.delivery.SupplierService
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.batchInsert
import simpleorm.core.filter.EqFilter
import simpleorm.core.findBy
import simpleorm.core.pagination.PageRequest
import simpleorm.core.pagination.Sort
import simpleorm.core.persist
import java.util.Date

@Service
class OrderCreateService(
        @Value("\${order.create.fetchSize}")
        private var fetchSize: Int,
        private val supplierService: SupplierService
) {

    private val log by LoggerDelegate()

    @Scheduled(fixedRateString = "\${order.create.fixedRateMillis}", initialDelay = 1000)
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun createOrder(){
        val newApplications = fetchNewApplications()
        log.error("fetched new applications: ${newApplications.size}")
        if(newApplications.isEmpty()){
            return
        }
        val savedOrder = persist(SupplierOrder(date = Date()))
        newApplications.forEach { application ->
            val applicationItems = application.items.map {applicationItem ->
                SupplierOrderItem(
                        supplierOrder = savedOrder,
                        product = applicationItem.product,
                        salesPoint = application.salesPoint,
                        count = applicationItem.count)
            }
            batchInsert(applicationItems)
        }
        setNotNew(newApplications)
        log.info("order created: $savedOrder")
        supplierService.applyOrder(savedOrder)
    }

    private fun setNotNew(applications: List<Application>){
        val updatedApplications = applications.map { it.copy(newFlag = null)}
        batchInsert(updatedApplications)
    }

    private fun fetchNewApplications(): List<Application>{
        val pr = PageRequest(0, fetchSize,
            listOf(Sort(Application::date, Sort.Order.ASC)))
        return Application::class.findBy(EqFilter(Application::newFlag, true), pr).values
                //в одном заказе - одна заявка от торговой точки, чтобы не нарушать уникальность индекса
                .associateBy { it.salesPoint.id }.values.toList()
    }

}