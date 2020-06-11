package paulpaulych.tradefirm.customer

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.*
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.delete
import simpleorm.core.findAll
import simpleorm.core.findBy
import simpleorm.core.pagination.Page
import simpleorm.core.pagination.PageRequest
import simpleorm.core.save
import kotlin.reflect.KClass

@Component
class PlainCustomerQuery: PlainQuery<Customer> {

    @Authorization("ROLE_ADMIN")
    suspend fun plainCustomersPage(filter: GraphQLFilter?, pageRequest: PageRequest): Page<Customer> {
        return Customer::class.findBy(
                toFetchFilter(Customer::class, filter),
                pageRequest
        )
    }

    @GraphQLIgnore
    override fun requestedKClass(): KClass<Customer> {
        return Customer::class
    }

}

@Component
class PlainCustomerMutation: Mutation{

    @Authorization("ROLE_ADMIN")
    suspend fun savePlainCustomers(values: List<Customer>): List<Customer> {
        return values.map {
            save(it)
        }
    }

    @Authorization("ROLE_ADMIN")
    suspend fun deletePlainCustomers(ids: List<Long>): List<Long>{
        ids.forEach{ Customer::class.delete(it)}
        return ids
    }
}