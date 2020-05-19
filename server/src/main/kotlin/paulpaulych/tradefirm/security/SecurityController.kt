package paulpaulych.tradefirm.security

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import paulpaulych.tradefirm.employee.Employee
import paulpaulych.tradefirm.employee.EmployeeService
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/security")
class SecurityController(
        private val employeeService: EmployeeService
){

    @GetMapping("/name")
    @PreAuthorize("hasRole('ADMIN')")
    fun name(): Mono<Any> {
        return ReactiveSecurityContextHolder.getContext()
                .map { it.authentication }
                .map { it.principal}
    }


    @GetMapping("/name2")
    @PreAuthorize("hasRole('USER')")
    fun name2(): Mono<Any> {
        return ReactiveSecurityContextHolder.getContext()
                .map { it.authentication }
                .map { it.principal}
    }

    @GetMapping("/employee")
    fun employee(): Mono<Employee>{
        println("employee requested")
        return employeeService.employee(1L)
    }
    
}
