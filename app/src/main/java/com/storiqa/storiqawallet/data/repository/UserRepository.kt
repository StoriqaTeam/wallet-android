package com.storiqa.storiqawallet.data.repository

import android.annotation.SuppressLint
import com.storiqa.storiqawallet.data.db.dao.UserDao
import com.storiqa.storiqawallet.data.db.entity.UserEntity
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.network.requests.UpdateUserRequest
import com.storiqa.storiqawallet.data.network.responses.UserInfoResponse
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import com.storiqa.storiqawallet.data.preferences.IUserDataStorage
import com.storiqa.storiqawallet.utils.SignUtil
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserRepository(private val userDao: UserDao,
                     private val walletApi: WalletApi,
                     private val appDataStorage: IAppDataStorage,
                     private val userDataStorage: IUserDataStorage,
                     private val signUtil: SignUtil) : IUserRepository {

    override fun getCurrentUser(): Flowable<UserEntity> {
        val email = appDataStorage.currentUserEmail
        return userDao.loadUserFlowable(email).subscribeOn(Schedulers.io()).distinct()
    }

    @SuppressLint("CheckResult")
    override fun refreshCurrentUser(): Single<UserInfoResponse> {
        val email = appDataStorage.currentUserEmail
        val signHeader = signUtil.createSignHeader(email)

        return walletApi
                .getUserInfo(signHeader.timestamp, signHeader.deviceId,
                        signHeader.signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .doOnSuccess { userDao.insert(UserEntity(it)) }
    }

    override fun loadUser(email: String): Single<UserInfoResponse> {
        val signHeader = signUtil.createSignHeader(email)

        return walletApi
                .getUserInfo(signHeader.timestamp, signHeader.deviceId,
                        signHeader.signature)
                .doOnSuccess { userDao.insert(UserEntity(it)) }
    }

    override fun updateUserProfile(firstName: String, lastName: String): Single<UserInfoResponse> {
        val email = appDataStorage.currentUserEmail
        val signHeader = signUtil.createSignHeader(email)

        val request = UpdateUserRequest(firstName = firstName, lastName = lastName)
        return walletApi
                .updateUser(signHeader.timestamp, signHeader.deviceId,
                        signHeader.signature, request)
                .doOnSuccess {
                    userDao.insert(UserEntity(it))
                    userDataStorage.email = it.email
                    userDataStorage.firstName = it.firstName
                    userDataStorage.lastName = it.lastName
                    userDataStorage.id = it.id
                }
    }
}