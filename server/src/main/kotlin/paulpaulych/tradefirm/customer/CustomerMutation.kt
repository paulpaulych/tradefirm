package paulpaulych.tradefirm.customer

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import simpleorm.core.persist

@Component
class CustomerMutation: Mutation{

    fun addCustomer(name: String): Customer{
        return persist(Customer(name = name))
    }

}