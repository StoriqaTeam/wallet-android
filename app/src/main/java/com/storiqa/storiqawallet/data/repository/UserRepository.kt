package com.storiqa.storiqawallet.data.repository

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.data.IAppDataStorage
import com.storiqa.storiqawallet.data.db.dao.UserDao
import com.storiqa.storiqawallet.data.db.entity.User
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.utils.SignUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepository(private val userDao: UserDao,
                     private val walletApi: WalletApi,
                     private val appDataStorage: IAppDataStorage,
                     private val signUtil: SignUtil) {

    fun getUser(email: String): Observable<User> {
        //return userDao.loadUserByEmail(email)
        return Observable.mergeDelayError(requestUserInfo().subscribeOn(Schedulers.io()),
                userDao.loadUserByEmail(email).subscribeOn(Schedulers.io())
        )
    }

    @SuppressLint("CheckResult")
    private fun requestUserInfo(): Observable<User> {
        val token = appDataStorage.token
        val email = appDataStorage.currentUserEmail
        val signHeader = signUtil.createSignHeader(email)

        val observableUser = walletApi
                .getUserInfo(signHeader.timestamp, signHeader.deviceId, signHeader.signature, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { User(it) }

        observableUser.doOnNext { userDao.insert(it) }

        return observableUser
    }

}