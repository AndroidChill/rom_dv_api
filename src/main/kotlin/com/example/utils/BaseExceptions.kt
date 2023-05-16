package com.example.utils

import io.ktor.http.*

val success = MetaData(
    code = HttpStatusCode.OK.value,
    message = null,
    detail = null
)

val UserNotExistException = MetaData(
    code = HttpStatusCode.NotFound.value,
    message = "user not found",
    detail = "user does not exist"
)

val DataIsIncorrectException = MetaData(
    code = HttpStatusCode.BadRequest.value,
    message = "invalid data",
    detail = "The data is not correct. Please check the correctness of the data"
)