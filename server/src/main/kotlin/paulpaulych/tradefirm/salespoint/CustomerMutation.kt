package paulpaulych.tradefirm.salespoint

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.salespoint.Customer
import simpleorm.core.persist

@Component
class CustomerMutation: Mutation{

    fun addCustomer(name: String): Customer {
        return persist(Customer(name = name))
    }

}