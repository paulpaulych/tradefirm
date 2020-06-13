package paulpaulych.tradefirm.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat

@Configuration
class ObjectMapperConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.dateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm")
        objectMapper.registerModule(KotlinModule())
        return objectMapper
    }

}