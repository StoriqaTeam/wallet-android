package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.network.responses.AccountResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface IAccountsRepository {

    fun getAccounts(userId: Long): Flowable<List<AccountEntity>>

    fun refreshAccounts(errorHandler: (Exception) -> Unit)

    fun updateAccounts(id: Long, email: String): Observable<ArrayList<AccountResponse>>

}