package paulpaulych.tradefirm.customer

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import simpleorm.core.save

@Component
class CustomerMutation: Mutation{

    fun addCustomer(name: String): Customer{
        return save(Customer(name = name))
    }

}