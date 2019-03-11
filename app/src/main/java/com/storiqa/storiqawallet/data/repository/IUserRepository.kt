package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.entity.UserEntity
import com.storiqa.storiqawallet.data.network.responses.UserInfoResponse
import io.reactivex.Flowable
import io.reactivex.Single

interface IUserRepository {

    fun getCurrentUser(): Flowable<UserEntity>

    fun refreshCurrentUser(): Single<UserInfoResponse>

    fun loadUser(email: String): Single<UserInfoResponse>

    fun updateUserProfile(firstName: String, lastName: String): Single<UserInfoResponse>

}