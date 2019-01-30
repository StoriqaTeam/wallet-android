package com.storiqa.storiqawallet.data.mapper

import com.storiqa.storiqawallet.data.db.entity.TransactionWithAddresses
import com.storiqa.storiqawallet.data.model.Transaction

interface ITransactionMapper {

    fun map(transaction: TransactionWithAddresses): Transaction

    fun map(transactions: List<TransactionWithAddresses>): List<Transaction>

}