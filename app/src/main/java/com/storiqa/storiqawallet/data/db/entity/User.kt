package com.storiqa.storiqawallet.data.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.storiqa.storiqawallet.network.responses.UserInfoResponse

@Entity
data class User(
        @PrimaryKey var email: String,
        @ColumnInfo(name = "id") var id: Long,
        @ColumnInfo(name = "first_name") var firstName: String,
        @ColumnInfo(name = "last_name") var lastName: String,
        @ColumnInfo(name = "phone") var phone: String?) {

    constructor(userInfoResponse: UserInfoResponse) : this(
            userInfoResponse.email,
            userInfoResponse.id,
            userInfoResponse.firstName,
            userInfoResponse.lastName,
            userInfoResponse.phone)

}