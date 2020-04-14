package paulpaulych.tradefirm.employee

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import simpleorm.core.findAll
import simpleorm.core.findById


@Component
class EmployeeQuery : Query {

    fun employee(id: Long): Employee{
        return Employee::class.findById(id)
                ?: error("employee not found")
    }

    fun employees(): List<Employee>{
        return Employee::class.findAll()
    }

}