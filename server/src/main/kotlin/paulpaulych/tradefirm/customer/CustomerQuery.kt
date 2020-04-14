package paulpaulych.tradefirm.customer

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import simpleorm.core.findAll
import simpleorm.core.findById

@Component
class CustomerQuery: Query {

    fun customer(id: Long): Customer{
        return Customer::class.findById(id)
                ?: error("customer not found")
    }

    fun customers(): List<Customer>{
        return Customer::class.findAll()
    }

}