package paulpaulych.tradefirm.apicore

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import paulpaulych.tradefirm.App
import paulpaulych.utils.LoggerDelegate

@SpringBootTest(classes = [App::class])
class AnalyticsQueryTest {

    @Autowired
    private lateinit var jdbc: JdbcTemplate

    private val log by LoggerDelegate()

    init {
//        jdbc.execute("create table product(id bigint primary key auto_increment, name text)")
    }

    @Test
    fun test(){
        log.info("config test passed")
    }
}