package com.storiqa.storiqawallet.utils

import com.storiqa.cryptokeys.IKeyGenerator
import com.storiqa.cryptokeys.ISigner
import com.storiqa.cryptokeys.PrivateKey
import com.storiqa.storiqawallet.data.model.SignHeader
import com.storiqa.storiqawallet.data.preferences.IAppDataStorage
import javax.inject.Inject

class SignUtil
@Inject
constructor(
        private val appData: IAppDataStorage,
        private val signer: ISigner,
        private val keyGenerator: IKeyGenerator) {

    fun createSignHeader(email: String): SignHeader {
        val deviceId = appData.deviceId
        val timestamp = System.currentTimeMillis().toString()

        val privateKeyHex = appData.getPrivateKey(email)
        val privateKey: PrivateKey
        if (privateKeyHex == null) {
            privateKey = keyGenerator.generatePrivateKey()
                    ?: throw Exception("Can't generate new private key")
            appData.setPrivateKey(email, privateKey.hex)
        } else
            privateKey = PrivateKey.fromHex(privateKeyHex)

        val publicKeyHex = privateKey.publicKey.hex

        val message = timestamp + deviceId

        val signature = signer.sign(message, privateKey)
                ?: throw Exception("Can't sign message")

        return SignHeader(deviceId, timestamp, signature, publicKeyHex)
    }

    //****  saving private privateKey to encrypted keystore  ****

    /*private val keyStoreFile = "KeyStore"
    private val keyAlias = "KeyAlias"

    private var privateKey: PrivateKey? = null
    private val signer: ISigner = Signer()

    fun loadKey(password: CharArray): Boolean {
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply { load(null) }

        try {
            context.openFileInput(keyStoreFile)
                    .use { fis -> keyStore.load(fis, password) }

            val keyRaw = keyStore.getPrivateKey(keyAlias, password).encoded
            privateKey = PrivateKey.fromRaw(keyRaw)
            return true
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return false
    }

    fun generateNewKey(password: CharArray) {
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply { load(null) }
        val protParam = KeyStore.PasswordProtection(password)

        val mySecretKey = KeyGenerator().generateSecretKey()
        val skEntry = KeyStore.SecretKeyEntry(mySecretKey)
        privateKey = PrivateKey.fromRaw(skEntry.secretKey.encoded)
        keyStore.setEntry(keyAlias, skEntry, protParam)

        context.openFileOutput(keyStoreFile, Context.MODE_PRIVATE)
                .use { fos -> keyStore.store(fos, password) }
    }

    fun deleteKeyStore() {
        context.deleteFile(keyStoreFile)
    }

    fun sign(message: String): String? {
        return signer.sign(message, privateKey!!)
    }*/
}