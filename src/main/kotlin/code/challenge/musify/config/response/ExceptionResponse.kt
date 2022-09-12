package code.challenge.musify.config.response

import org.springframework.http.HttpStatus

data class ExceptionResponse(
    val error: String? = "An unexpected error has occurred",
    val status: Int,
    val statusText: HttpStatus,
)
