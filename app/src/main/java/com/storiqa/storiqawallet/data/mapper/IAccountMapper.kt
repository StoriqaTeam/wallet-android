package com.storiqa.storiqawallet.data.mapper

import com.storiqa.storiqawallet.data.db.entity.AccountEntity
import com.storiqa.storiqawallet.data.model.Account

interface IAccountMapper {

    fun map(account: AccountEntity): Account

}