package com.victor.guiabolso.service

import com.victor.guiabolso.model.Transaction
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@Service
class TransactionService {
    val max_size = 60
    fun createTransaction(id: Int, year: Int, month: Int, value: Int? = Random.nextInt(-9999999, 9999999)) =
            if (validateValue(value!!))
                Transaction(
                        description = getValueAndDescription(value),
                        date = createDate(month, year),
                        duplicated = false,
                        value = BigDecimal(value).movePointLeft(2)
                )
            else
                throw Exception("Invalid value!")


    fun validateId(id: Int): Boolean =
            id in 1000..100000000

    fun validateValue(value: Int): Boolean =
            value in -9999999..9999999

    fun createDuplicates(id: Int, year: Int, month: Int): Triple<Transaction, Transaction, Transaction> {
        createTransaction(id, year, month).also { it ->
            when {
                it.description.length in 11..60 -> {
                    return Triple(it, it, it.copy(duplicated = true))
                }
                it.description.length > max_size -> {
                    it.copy(description = it.description.substring(0, 60)).also {
                        return Triple(it, it, it.copy(duplicated = true))
                    }
                }
                else -> {
                    throw Exception(it.description + " Invalid description, must contain at least 10 characters")
                }
            }
        }
    }

    fun checkDescription(obj: Transaction) =
            when {
                obj.description.length < 10 -> false
                else -> true
            }

    fun getValueAndDescription(value: Int) =
            when {
                value < 0 -> getDebitsDescriptions()
                else -> getCreditsDescriptions()
            }

    fun createDate(month: Int, year: Int): Long =
            when {
                month == 2 -> Random.nextInt(1, 28)
                else -> Random.nextInt(1, 30)
            }.let {
                LocalDate.parse("${it}-${month}-${year}", DateTimeFormatter.ofPattern("d-M-yyyy"))
                        .atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond
            }


    fun getDebitsDescriptions() =
            listOf(
                    "Compra Uber",
                    "Compra iFood",
                    "Compra MercadoLivre",
                    "Compra Farmácia",
                    "Conserto Celular",
                    "Conta Internet",
                    "Consumir batata ajuda a prevenir doenças cardíacas. Isso ocorre devido as fibras, vitamina C e vitamina B6 presentes no alimento. Além disso, as batatas contêm boas quantidades de potássio, mineral que desempenha um papel importante para manter o ritmo cardíaco normal e no controle da pressão arterial."
            ).let { debitList ->
                debitList.get(Random.nextInt(0, debitList.lastIndex))
            }

    fun getCreditsDescriptions() =
            listOf(
                    "Venda Computador",
                    "Recebimento de dívida",
                    "Recebimento aluguel",
                    "Depósito do salario",
                    "Em grandes quantidades, a batata pode ser prejudicial, seja no ganho de peso, seja no excesso de alguns nutrientes. Por isso, pessoas com problemas renais ou cardíacos, em uso de algumas medicações específicas, devem ficar atentas à quantidade de potássio e fósforo que a batata-inglesa contém."
            ).let { creditList ->
                creditList.get(Random.nextInt(0, creditList.lastIndex))
            }

    fun createList(id: Int, year: Int, month: Int): MutableList<Transaction> {
        val list = mutableListOf<Transaction>()
        for (i in 1..3) {
            createDuplicates(id, year, month).also {
                list.add(it.first)
                list.add(it.second)
                list.add(it.third)
            }
        }
        return list
    }
}