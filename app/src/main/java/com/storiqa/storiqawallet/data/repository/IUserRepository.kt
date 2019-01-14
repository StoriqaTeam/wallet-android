package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.entity.User
import io.reactivex.Flowable

interface IUserRepository {

    fun getUser(email: String): Flowable<User>

    fun updateUser(errorHandler: (Exception) -> Unit)

}