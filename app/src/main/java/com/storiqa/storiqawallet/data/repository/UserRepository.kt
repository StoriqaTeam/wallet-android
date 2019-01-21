package com.storiqa.storiqawallet.data.repository

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.db.dao.UserDao
import com.storiqa.storiqawallet.data.db.entity.User
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.responses.UserInfoResponse
import com.storiqa.storiqawallet.utils.SignUtil
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepository(private val userDao: UserDao,
                     private val walletApi: WalletApi,
                     private val appDataStorage: IAppDataStorage,
                     private val signUtil: SignUtil) : IUserRepository {

    override fun getUser(email: String): Flowable<User> {
        return userDao.loadUserFlowable(email).subscribeOn(Schedulers.io()).distinct()
    }

    @SuppressLint("CheckResult")
    override fun refreshUser(errorHandler: (Exception) -> Unit) {
        val token = appDataStorage.token

        val email = appDataStorage.currentUserEmail
        val signHeader = signUtil.createSignHeader(email)

        walletApi
                .getUserInfo(signHeader.timestamp, signHeader.deviceId,
                        signHeader.signature, "Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { errorHandler(it as Exception) }
                .observeOn(Schedulers.io())
                .doOnNext { userDao.insert(User(it)) }
                .subscribe()
    }

    override fun updateUser(email: String): Observable<UserInfoResponse> {
        val token = appDataStorage.token
        val signHeader = signUtil.createSignHeader(email)

        return walletApi
                .getUserInfo(signHeader.timestamp, signHeader.deviceId,
                        signHeader.signature, "Bearer $token")
                .doOnNext { userDao.insert(User(it)) }
    }
}