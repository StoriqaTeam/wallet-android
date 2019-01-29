package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.entity.TransactionAccountEntity
import com.storiqa.storiqawallet.data.db.entity.TransactionWithAddresses
import com.storiqa.storiqawallet.network.responses.TransactionResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface ITransactionsRepository {

    fun getTransactions(): Flowable<List<TransactionWithAddresses>>

    fun getTransactionAccounts(): Flowable<List<TransactionAccountEntity>>

    fun refreshTransactions(id: Long, email: String): Observable<List<TransactionResponse>>

}