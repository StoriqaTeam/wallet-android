package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.entity.Account
import io.reactivex.Flowable
import io.reactivex.Observable

interface IAccountsRepository {

    fun getAccounts(userId: Long): Flowable<List<Account>>

    fun refreshAccounts(errorHandler: (Exception) -> Unit)

    fun updateAccounts(id: Long, email: String): Observable<ArrayList<com.storiqa.storiqawallet.data.model.Account>>

}