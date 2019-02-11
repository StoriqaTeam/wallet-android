package com.storiqa.storiqawallet.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.storiqa.storiqawallet.data.network.responses.UserInfoResponse

@Entity(tableName = "Users",
        indices = [Index(value = arrayOf("id"),
        unique = true)])
data class UserEntity(
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