package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.entity.User
import com.storiqa.storiqawallet.network.responses.UserInfoResponse
import io.reactivex.Flowable
import io.reactivex.Observable

interface IUserRepository {

    fun getUser(email: String): Flowable<User>

    fun updateUser(): Observable<UserInfoResponse>

}