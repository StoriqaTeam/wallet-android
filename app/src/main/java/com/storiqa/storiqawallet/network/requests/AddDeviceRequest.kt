package com.storiqa.storiqawallet.network.requests

class AddDeviceRequest(
        val userId: Int,
        val deviceId: String,
        val publicKey: String = "0482b233ccc3c44657212e16b0b0e699494ea082030f403034ce16f06630fde5886f89f95ef01ad4e3cad2a95b7d2aee08b0a37a400bb989ff9002cdf7efd7cd5a") {

    private val deviceOs = "android"
}