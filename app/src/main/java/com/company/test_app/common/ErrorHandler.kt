package com.company.test_app.common

import com.company.test_app.domain.exception.*

/**
 * @param message function returns message if unknown code was detected
 */
fun Int.throwException(message: String): Exception {
    return when (this) {
        429 -> {
            RequestsLimitException()
        }
        401 -> UnauthorizedException()
        400 -> {
            BadRequestException()
        }
        404 -> {
            NotFoundException()
        }
        403 -> {
            NotValidException()
        }
        409 -> {
            ConflictException()
        }
        else -> {
            NetworkErrorException(message)
        }
    }
}