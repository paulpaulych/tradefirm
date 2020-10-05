package paulpaulych.tradefirm.config.graphql

import com.expediagroup.graphql.spring.exception.SimpleKotlinGraphQLError
import graphql.ErrorClassification
import graphql.GraphQLError
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import graphql.language.SourceLocation
import org.springframework.stereotype.Component
import paulpaulych.utils.LoggerDelegate

class ExpectedError(
        message: String
): RuntimeException(message)

class BadInputError(
        message: String
): RuntimeException(message)

internal fun expectedError(message: String): Nothing{
    throw ExpectedError(message)
}

internal fun badInputError(message: String): Nothing{
    throw BadInputError(message)
}

@Component
class ExceptionHandler : DataFetcherExceptionHandler{

    private val log by LoggerDelegate()

    override fun onException(handlerParameters: DataFetcherExceptionHandlerParameters): DataFetcherExceptionHandlerResult {
        val exception = handlerParameters.exception
        log.warn(exception.message, exception)

        if(exception is ExpectedError){
            return onExpectedError(exception.message!!)
        }
        val sourceLocation = handlerParameters.sourceLocation
        val path = handlerParameters.path
        val error: GraphQLError = SimpleKotlinGraphQLError(exception = exception, locations = listOf(sourceLocation), path = path.toList())
        return DataFetcherExceptionHandlerResult.newResult(error).build()
    }

    fun onExpectedError(message: String): DataFetcherExceptionHandlerResult{
        val error: GraphQLError = ExpectedGraphQLError(message = message)
        return DataFetcherExceptionHandlerResult.newResult(error).build()
    }

    fun onBadInputError(message: String): DataFetcherExceptionHandlerResult{
        val error: GraphQLError = BadRequestGraphQLError(message = message)
        return DataFetcherExceptionHandlerResult.newResult(error).build()
    }
}

class BadRequestGraphQLError(
        private val message: String
): GraphQLError{
    override fun getMessage(): String = message

    override fun getErrorType(): ErrorClassification{
        return ErrorType.BAD_REQUEST_ERROR
    }

    override fun getLocations(): MutableList<SourceLocation> {
        return mutableListOf()
    }
}

class ExpectedGraphQLError(
        private val message: String
): GraphQLError{

    override fun getMessage(): String = message

    override fun getErrorType(): ErrorClassification{
        return ErrorType.READABLE_ERROR
    }

    override fun getLocations(): MutableList<SourceLocation> {
        return mutableListOf()
    }
}

enum class ErrorType: ErrorClassification{
    READABLE_ERROR,
    BAD_REQUEST_ERROR
}