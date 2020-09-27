package com.victor.guiabolso.service

import com.victor.guiabolso.model.response.ErrorResponse
import com.victor.guiabolso.model.response.TransactionResponse
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.annotation.Lazy
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
@Lazy
class TransactionAPIService(private val restTemplate: TestRestTemplate, @LocalServerPort private val port: Int) {
    private fun buildCallUrl(test: String) = "http://localhost:${port}${test}"


    fun creatingTest(id: Int, year: Int, month: Int): ResponseEntity<TransactionResponse>? =
            restTemplate.getForEntity(buildCallUrl("/${id}/transacoes/${year}/${month}"), TransactionResponse::class.java)

    fun errorCreatingTest(id: Int, year: Int, month: Int): ResponseEntity<ErrorResponse>? =
            restTemplate.getForEntity(buildCallUrl("/${id}/transacoes/${year}/${month}"), ErrorResponse::class.java)


}
