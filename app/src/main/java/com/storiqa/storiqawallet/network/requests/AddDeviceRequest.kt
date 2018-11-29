package com.storiqa.storiqawallet.network.requests

class AddDeviceRequest(
        val userId: Int = 140,
        val deviceId: String = "09bbda10-2908-4c5a-bd63-9098fc6bffff",
        val publicKey: String = "04b569b0d199212a2d111af0d5e35b0ca52b2206799766bcaf2c9ef230ad330da18bd480dc707640880e73a3abbe06d903deae95cb2a1ed7651aa36e0b0d640319") {

    private val deviceOs = "android"
}