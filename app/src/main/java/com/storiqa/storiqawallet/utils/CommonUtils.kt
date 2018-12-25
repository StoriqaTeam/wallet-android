package com.storiqa.storiqawallet.utils

import com.storiqa.cryptokeys.PrivateKey
import com.storiqa.cryptokeys.Signer

fun getTimestamp() = System.currentTimeMillis().toString()

fun getDeviceId() = "09bbda10-2908-4c5a-bd63-9098fc6bffxx"

fun getSign(timestamp: String, deviceId: String): String? {
    val testKey = "9cd8d1936db8447caa6f08e0383dacb732ac6527128acaae7b9405d252f155ff"
    val privateKey = PrivateKey.fromHex(testKey)
    val pubKey = privateKey.publicKey.hex //0482b233ccc3c44657212e16b0b0e699494ea082030f403034ce16f06630fde5886f89f95ef01ad4e3cad2a95b7d2aee08b0a37a400bb989ff9002cdf7efd7cd5a
    val sign = Signer().sign(timestamp + deviceId, privateKey)
    return sign
}