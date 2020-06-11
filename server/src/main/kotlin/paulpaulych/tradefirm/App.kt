package paulpaulych.tradefirm

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
class App

fun main(args: Array<String>) {
    applicationContext = SpringApplication.run(App::class.java, *args)

}

lateinit var applicationContext: ApplicationContext
