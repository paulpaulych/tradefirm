package paulpaulych.tradefirm.customer

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.apicore.GraphQLFilter
import paulpaulych.tradefirm.apicore.GraphQLFilterMapper
import paulpaulych.tradefirm.apicore.PageInfo
import paulpaulych.tradefirm.apicore.PlainQuery
import paulpaulych.tradefirm.security.Authorization
import paulpaulych.utils.LoggerDelegate
import simpleorm.core.delete
import simpleorm.core.findAll
import simpleorm.core.findBy
import simpleorm.core.pagination.PageRequest
import simpleorm.core.save
import kotlin.reflect.KClass

data class CustomerPage (
        val values: List<Customer>,
        val pageInfo: PageInfo
)

@Component
class PlainCustomerQuery(
        private val filterMapper: GraphQLFilterMapper
): PlainQuery<Customer> {

    private val log by LoggerDelegate()

    @Authorization("ROLE_ADMIN")
    suspend fun plainCustomersPage(filter: GraphQLFilter?, pageRequest: PageRequest): CustomerPage {
        log.info("hello from ")
        val res = if(filter == null){
            Customer::class.findAll(pageRequest)
        } else {
            Customer::class.findBy(
                    filterMapper.getFetchFilter(Customer::class, filter),
                    pageRequest
            )
        }
        return CustomerPage(res.values, PageInfo(res.values.size))
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
        println(ids)
        ids.forEach{ Customer::class.delete(it)}
        return ids
    }
}