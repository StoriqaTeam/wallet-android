package com.storiqa.storiqawallet.data

interface IAppDataStorage {

    var isFirstLaunch: Boolean
    var isPinEntered: Boolean
    var pin: String
    var deviceId: String
    var token: String
    var currentUserEmail: String
    var oldestPendingTransactionTime: Long
    val deviceOs: String

    fun setPrivateKey(email: String, key: String)
    fun getPrivateKey(email: String): String?
}