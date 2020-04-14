package paulpaulych.tradefirm.customer

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import simpleorm.core.save

@Component
class CustomerMutation: Mutation{

    fun addCustomer(customer: Customer): Customer{
        return save(customer)
    }

//    fun deleteCustomer(id: Long){
//        Customer::class.delete(id)
//    }

}