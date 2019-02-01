package com.storiqa.storiqawallet.data.mapper

import com.storiqa.storiqawallet.data.db.entity.TransactionWithAddresses
import com.storiqa.storiqawallet.data.model.Transaction

interface ITransactionMapper {

    fun map(transaction: TransactionWithAddresses, address: String): Transaction

    fun map(transactions: List<TransactionWithAddresses>, address: String): List<Transaction>

}