package paulpaulych.tradefirm.config.graphql

import graphql.schema.Coercing
import org.springframework.stereotype.Component
import paulpaulych.utils.LoggerDelegate
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT_STRING = "dd-MM-yyyy HH:mm:ss Z"

@Component
object DateCoercing : Coercing<Date, String> {

    private val format = SimpleDateFormat(DATE_FORMAT_STRING)
    private val log by LoggerDelegate()

    override fun parseValue(input: Any?): Date? {
        if(input == null){
            return null
        }
        return format.parse(input as String)
    }

    override fun parseLiteral(input: Any?): Date? {
        if(input == null){
            return null
        }
        return format.parse(input as String)
    }

    override fun serialize(dataFetcherResult: Any?): String? {
        if(dataFetcherResult == null){
            return null
        }
        return format.format(dataFetcherResult as Date)
    }
}