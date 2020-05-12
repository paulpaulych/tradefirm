package paulpaulych.tradefirm.employee

import com.expediagroup.graphql.spring.operations.Query
import kotlinx.coroutines.runBlocking
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import paulpaulych.tradefirm.area.Area
import paulpaulych.tradefirm.salespoint.SalesPoint
import paulpaulych.tradefirm.security.Role
import paulpaulych.utils.LoggerDelegate
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import simpleorm.core.findAll
import simpleorm.core.findById
import java.math.BigDecimal

@Component
class EmployeeQuery(
        private val employeeService: EmployeeService
) : Query {

    private val log by LoggerDelegate()

    fun employee(id: Long): Mono<Employee> {
        log.info("employees requested")
        return employeeService.employee(id)
//        println("employees requested")
//        val employee = Employee::class.findById(id)
////                ?: error("data not found")
//        if(employee != null){
//            return Mono.just(employee)
//        }
//        return Mono.empty()
//        log.error("$employee")
//        if(employee != null){
//            return Mono.just(Employee(1, "РАФШАН",
//                        SalesPoint(
//                                1,
//                                SalesPoint.Type.MAGAZIN.name,
//                                Area(
//                                        1,
//                                        1L,
//                                        BigDecimal.ONE,
//                                        BigDecimal.ONE,
//                                        1
//                                )), BigDecimal.ONE))
//        }
//        return Mono.empty()
////                ?. toMono()
//                ?: Mono.error(RuntimeException("employee not found"))
    }

    fun employees(): Flux<Employee> {
        return Employee::class.findAll().toFlux()
    }

    fun string(): Mono<String>{
        return Mono.just("hello")
    }

}

@Component
class EmployeeService{

    @PreAuthorize("hasRole('USER')")
    fun employee(id: Long): Mono<Employee>{
        val employee = Employee::class.findById(id)
//                ?: error("data not found")
        if(employee != null){
            return Mono.just(employee)
        }
        return Mono.empty()
//
    }

}