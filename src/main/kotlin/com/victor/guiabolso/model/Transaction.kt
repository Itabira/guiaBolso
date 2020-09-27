package com.victor.guiabolso.model

import java.math.BigDecimal
import javax.validation.constraints.Size

data class Transaction(
        @Size(min = 10, max = 60)
        val description: String,
        val date: Long,
        val value: BigDecimal,
        val duplicated: Boolean
)
