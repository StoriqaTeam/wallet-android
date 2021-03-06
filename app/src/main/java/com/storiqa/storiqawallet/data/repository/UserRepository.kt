package com.storiqa.storiqawallet.data.repository

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.data.db.dao.UserDao
import com.storiqa.storiqawallet.data.db.entity.UserEntity
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.network.responses.UserInfoResponse
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.utils.SignUtil
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepository(private val userDao: UserDao,
                     private val walletApi: WalletApi,
                     private val appDataStorage: IAppDataStorage,
                     private val signUtil: SignUtil) : IUserRepository {

    override fun getUser(email: String): Flowable<UserEntity> {
        return userDao.loadUserFlowable(email).subscribeOn(Schedulers.io()).distinct()
    }

    @SuppressLint("CheckResult")
    override fun refreshUser(): Observable<UserInfoResponse> {
        val token = appDataStorage.token

        val email = appDataStorage.currentUserEmail
        val signHeader = signUtil.createSignHeader(email)

        return walletApi
                .getUserInfo(signHeader.timestamp, signHeader.deviceId,
                        signHeader.signature, "Bearer $token")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .doOnNext { userDao.insert(UserEntity(it)) }
    }

    override fun updateUser(email: String): Observable<UserInfoResponse> {
        val token = appDataStorage.token
        val signHeader = signUtil.createSignHeader(email)

        return walletApi
                .getUserInfo(signHeader.timestamp, signHeader.deviceId,
                        signHeader.signature, "Bearer $token")
                .doOnNext { userDao.insert(UserEntity(it)) }
                .doOnError { }
    }
}