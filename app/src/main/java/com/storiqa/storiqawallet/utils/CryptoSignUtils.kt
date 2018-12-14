package com.storiqa.storiqawallet.utils

import android.content.Context
import com.storiqa.cryptokeys.ISigner
import com.storiqa.cryptokeys.KeyGenerator
import com.storiqa.cryptokeys.PrivateKey
import com.storiqa.cryptokeys.Signer
import com.storiqa.storiqawallet.di.qualifiers.AppContext
import java.security.KeyStore
import javax.inject.Inject

class CryptoSignUtils
@Inject
constructor(@AppContext private val context: Context) {

    private val keyStoreFile = "KeyStore"
    private val keyAlias = "KeyAlias"

    private var privateKey: PrivateKey? = null
    private val signer: ISigner = Signer()

    fun loadKey(password: CharArray): Boolean {
        val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply { load(null) }

        try {
            context.openFileInput(keyStoreFile)
                    .use { fis -> keyStore.load(fis, password) }

            val keyRaw = keyStore.getKey(keyAlias, password).encoded
            privateKey = PrivateKey.fromRaw(keyRaw)
            return true
        } catch (exception: Exception) {
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
    }
}