package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.network.responses.TransactionResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface ITransactionsRepository {

    fun getAllTransactionsByAddress(address: String): Flowable<List<Transaction>>

    fun getTransactionsByAddress(address: String, limit: Int): Flowable<List<Transaction>>

    fun getTransactionById(address: String, id: String): Flowable<Transaction>

    fun refreshTransactions(id: Long, email: String, offset: Int): Observable<List<TransactionResponse>>

}