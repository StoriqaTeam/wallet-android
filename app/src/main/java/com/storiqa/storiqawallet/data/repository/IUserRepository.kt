package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.entity.UserEntity
import com.storiqa.storiqawallet.network.responses.UserInfoResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface IUserRepository {

    fun getUser(email: String): Flowable<UserEntity>

    fun refreshUser(errorHandler: (Exception) -> Unit)

    fun updateUser(email: String): Observable<UserInfoResponse>

}