package paulpaulych.tradefirm.config.orm

import paulpaulych.utils.LoggerDelegate
import simpleorm.core.jdbc.*
import java.sql.Connection
import org.springframework.jdbc.core.ConnectionCallback as SpringConnectionCallback
import org.springframework.jdbc.core.JdbcTemplate as SpringJdbcTemplate

class SpringJdbcAdapter(
        private val jdbc: SpringJdbcTemplate
): JdbcOperations {

    override val log by LoggerDelegate()

    override fun execute(sql: String) {
        jdbc.execute(sql)
    }

    override fun <T : Any> doInConnection(callback: (Connection) -> T): T {
        return jdbc.execute(SpringConnectionCallback<T>{callback.invoke(it) })
                ?: error("null value got")
    }


}