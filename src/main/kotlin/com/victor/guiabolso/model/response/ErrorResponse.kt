package com.victor.guiabolso.model.response

import java.time.ZonedDateTime

data class ErrorResponse(
        val errorCode: String,
        val errorMessage: String,
        val dateTime: ZonedDateTime = ZonedDateTime.now()
) {
}