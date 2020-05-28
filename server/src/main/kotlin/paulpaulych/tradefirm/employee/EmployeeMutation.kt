package paulpaulych.tradefirm.employee

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.salespoint.SalesPoint2
import simpleorm.core.findById
import simpleorm.core.save

@Component
class EmployeeMutation: Mutation{

    @Secured
    fun saveEmployee(employee: EmployeeInput): Employee{

        val salesPoint = SalesPoint2::class.findById(employee.salesPointId)
                ?: error("sales point with id = ${employee.salesPointId} not found")

        return save(Employee(
                id = employee.id,
                name = employee.name,
                salesPoint2 = salesPoint,
                salary = employee.salary
        ))

    }
}