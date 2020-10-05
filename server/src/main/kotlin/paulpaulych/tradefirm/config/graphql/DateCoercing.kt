package paulpaulych.tradefirm.config.graphql

import graphql.schema.Coercing
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT_STRING = "dd-MM-yyyy HH:mm:ss"

object DateCoercing : Coercing<Date, String> {

    private val format = SimpleDateFormat(DATE_FORMAT_STRING)

    override fun parseValue(input: Any?): Date? {
        if(input == null){
            return null
        }
        return format.parse(input as String)
    }

    override fun parseLiteral(input: Any?): Date? {
        return parseValue(input)
    }

    override fun serialize(dataFetcherResult: Any?): String? {
        if(dataFetcherResult == null){
            return null
        }
        return format.format(dataFetcherResult as Date)
    }
}