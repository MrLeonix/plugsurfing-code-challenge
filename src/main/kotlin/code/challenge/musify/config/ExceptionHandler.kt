package code.challenge.musify.config

import code.challenge.musify.config.response.ExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(Exception::class)
    fun handleException(
        exception: Exception,
        request: WebRequest
    ): ResponseEntity<ExceptionResponse> {
        val httpStatus = HttpStatus.BAD_REQUEST

        val error = ExceptionResponse(
            exception.message,
            httpStatus.value(),
            httpStatus
        )

        return ResponseEntity(
            error,
            httpStatus
        )
    }
}
