package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.model.Transaction
import com.storiqa.storiqawallet.network.responses.TransactionResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface ITransactionsRepository {

    fun getTransactions(): Flowable<List<Transaction>>

    fun refreshTransactions(id: Long, email: String): Observable<List<TransactionResponse>>

}