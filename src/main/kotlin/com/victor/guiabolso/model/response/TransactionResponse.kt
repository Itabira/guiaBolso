package com.victor.guiabolso.model.response

import com.victor.guiabolso.model.Transaction

data class TransactionResponse(val transactions: MutableList<Transaction>)