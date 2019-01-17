package com.storiqa.storiqawallet.data.repository

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.db.dao.AccountDao
import com.storiqa.storiqawallet.data.db.dao.UserDao
import com.storiqa.storiqawallet.data.db.entity.Account
import com.storiqa.storiqawallet.data.db.entity.User
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.utils.SignUtil
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AccountsRepository(private val userDao: UserDao,
                         private val accountDao: AccountDao,
                         private val walletApi: WalletApi,
                         private val appDataStorage: IAppDataStorage,
                         private val signUtil: SignUtil) : IAccountsRepository {

    override fun getAccounts(userId: Long): Flowable<List<Account>> {
        return accountDao.loadAccounts(userId).subscribeOn(Schedulers.io())
    }

    @SuppressLint("CheckResult")
    override fun refreshAccounts(errorHandler: (Exception) -> Unit) {
        val email = appDataStorage.currentUserEmail
        userDao.loadUserSingle(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ requestAccounts(it, errorHandler) }, { errorHandler(it as Exception) })
    }

    @SuppressLint("CheckResult")
    private fun requestAccounts(user: User, errorHandler: (Exception) -> Unit) {
        val email = appDataStorage.currentUserEmail
        val token = appDataStorage.token
        val signHeader = signUtil.createSignHeader(email)

        walletApi
                .getAccounts(user.id, signHeader.timestamp, signHeader.deviceId,
                        signHeader.signature, "Bearer $token", 0, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { errorHandler(it as Exception) }
                .observeOn(Schedulers.io())
                .doOnNext { saveAccounts(it) }
                .subscribe()
    }

    private fun saveAccounts(accounts: ArrayList<com.storiqa.storiqawallet.data.model.Account>) {
        val accountsList = ArrayList<Account>()
        accounts.forEach { accountsList.add(Account(it)) }

        accountDao.deleteAndInsertAll(accountsList)
    }
}