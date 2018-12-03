package com.storiqa.storiqawallet.utils

import android.os.Build
import com.storiqa.cryptokeys.PrivateKey
import com.storiqa.cryptokeys.Signer

fun getDeviceOs() = Build.VERSION.SDK_INT.toString()

fun getTimestamp() = System.currentTimeMillis().toString()

fun getDeviceId() = "09bbda10-2908-4c5a-bd63-9098fc6bffff"

fun getSign(timestamp: String, deviceId: String): String? {
    val testKey = "9cd8d1936db8447caa6f08e0383dacb732ac6527128acaae7b9415d252f155ff"
    val privateKey = PrivateKey.fromHex(testKey)
    val pubKey = privateKey.publicKey.hex
    val sign = Signer().sign(timestamp + deviceId, privateKey)
    return sign
}