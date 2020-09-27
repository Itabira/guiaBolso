package com.victor.guiabolso.controller

import com.victor.guiabolso.model.response.ErrorResponse
import com.victor.guiabolso.model.response.TransactionResponse
import com.victor.guiabolso.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
class TransactionController(val transactionService: TransactionService) {

    @GetMapping("/{id}/transacoes/{ano}/{mes}")
    fun getTransactions(
            @PathVariable(name = "id") id: Int,
            @PathVariable(name = "ano") year: Int,
            @PathVariable(name = "mes") month: Int
    ) = toResponse(id, year, month)

    fun buildErrorResponse(errorCode: String) =
            ErrorResponse(errorCode = errorCode, errorMessage = "Invalid id, must be between 1.000 and 100.000.000", dateTime = ZonedDateTime.now())

    fun toResponse(id: Int, year: Int, month: Int) =
            if (transactionService.validateId(id))
                ResponseEntity.ok(TransactionResponse(transactionService.createList(id, year, month)))
            else
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildErrorResponse("1"))
    
}