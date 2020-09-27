package com.victor.guiabolso

import com.victor.guiabolso.service.TransactionAPIService
import com.victor.guiabolso.service.TransactionService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainTests {

    @Autowired
    private lateinit var transactionAPIService: TransactionAPIService

    @Test
    fun testID() {
        val year = 2020
        val month = 5

        var userId = 1000
        transactionAPIService.creatingTest(userId, year, month).also {
            assertEquals(HttpStatus.OK, it?.statusCode)
        }

        userId = 25
        transactionAPIService.errorCreatingTest(userId, year, month)?.also {
            assertEquals(HttpStatus.BAD_REQUEST, it.statusCode)
            it.body?.also {
                assertEquals(it.errorCode, "1")
            }
        }

        userId = 100000001
        transactionAPIService.errorCreatingTest(userId, year, month)?.also {
            assertEquals(HttpStatus.BAD_REQUEST, it.statusCode)
            it.body?.also {
                assertEquals(it.errorCode, "1")
            }
        }

    }

    @Test()
    fun testValue() {
        val year = 2020
        val month = 5
        val userId = 1000

        var value = -99999997
        assertThrows<Exception> {
            TransactionService().createTransaction(id = userId, year = year, month = month, value = value)
        }

        value = 99999997
        assertThrows<Exception> {
            TransactionService().createTransaction(id = userId, year = year, month = month, value = value)
        }

        value = -9999999
        assertNotNull(TransactionService().createTransaction(id = userId, year = year, month = month, value = value))

        value = 9999999
        assertNotNull(TransactionService().createTransaction(id = userId, year = year, month = month, value = value))

    }


}
