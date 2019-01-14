package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.entity.Account
import io.reactivex.Flowable

interface IAccountRepository {

    fun getAccounts(userId: Long): Flowable<List<Account>>

    fun refreshAccounts(errorHandler: (Exception) -> Unit)

}