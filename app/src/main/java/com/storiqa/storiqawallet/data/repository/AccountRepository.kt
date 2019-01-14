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

class AccountRepository(private val userDao: UserDao,
                        private val accountDao: AccountDao,
                        private val walletApi: WalletApi,
                        private val appDataStorage: IAppDataStorage,
                        private val signUtil: SignUtil) : IAccountRepository {

    override fun getAccounts(userId: Long): Flowable<List<Account>> {
        return accountDao.loadAccounts(userId)
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
                .subscribe({ print(it) }, { errorHandler(it as Exception) })
    }
}