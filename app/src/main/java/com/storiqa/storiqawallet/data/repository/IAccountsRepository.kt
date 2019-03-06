package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.model.Account
import com.storiqa.storiqawallet.data.network.responses.AccountResponse
import io.reactivex.Flowable
import io.reactivex.Single

interface IAccountsRepository {

    var currentAccountPosition: Int

    fun getAccounts(userId: Long): Flowable<List<Account>>

    fun refreshAccounts(errorHandler: (Exception) -> Unit)

    fun updateAccounts(id: Long, email: String): Single<ArrayList<AccountResponse>>

}