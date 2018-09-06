package com.storiqa.storiqawallet.objects

import android.Manifest
import android.annotation.TargetApi
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.facebook.internal.FacebookRequestErrorClassification
import java.io.IOException
import java.security.InvalidAlgorithmParameterException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.cert.CertificateException
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey

@TargetApi(Build.VERSION_CODES.M)
class FingerprintHepler(private val context: Context, private val authCallback : FingerprintManager.AuthenticationCallback? = null ) {

    private lateinit var fingerprintManager: FingerprintManager
    private lateinit var keyguardManager: KeyguardManager

    private lateinit var keyStore: KeyStore
    private lateinit var keyGenerator: KeyGenerator
    private lateinit var cipher: Cipher
    private lateinit var cryptoObject: FingerprintManager.CryptoObject
    var errorCallback: onFingerPrintScarErrorOccured? = null


    interface onFingerPrintScarErrorOccured {
        fun onScreenSecureNotEnabled()
        fun onPermissionToScanFingerNotGranted()
        fun onNoFingersRegisteredInPhone()
    }

    init { initFingerprintScaner() }

    private fun initFingerprintScaner() {
        keyguardManager = context.getSystemService(AppCompatActivity.KEYGUARD_SERVICE) as KeyguardManager
        fingerprintManager = context.getSystemService(AppCompatActivity.FINGERPRINT_SERVICE) as FingerprintManager


        if (isNotSecuredByKeyguard()) {
            errorCallback?.onScreenSecureNotEnabled()
            return
        }

        if (isPermissionNotGranted()) {
            errorCallback?.onPermissionToScanFingerNotGranted()
            return
        }

        if (isNoFingersRegistered()) {
            errorCallback?.onNoFingersRegisteredInPhone()
            return
        }

        generateKey()

        if (cipherInit()) {
            cryptoObject = FingerprintManager.CryptoObject(cipher)
        }
    }

    private fun isNoFingersRegistered() = !fingerprintManager.hasEnrolledFingerprints()

    private fun isNotSecuredByKeyguard() = !keyguardManager.isKeyguardSecure

    private fun isPermissionNotGranted() = ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED

    fun isFingerprintSetupNotAvailable() : Boolean = android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M || isNoFingersRegistered() || isNotSecuredByKeyguard() || isPermissionNotGranted()

    @RequiresApi(Build.VERSION_CODES.M)
    protected fun generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException("Failed to get KeyGenerator instance", e)
        } catch (e: NoSuchProviderException) {
            throw RuntimeException("Failed to get KeyGenerator instance", e)
        }

        try {
            keyStore.load(null)
            keyGenerator.init(KeyGenParameterSpec.Builder(FacebookRequestErrorClassification.KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(
                            KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build())
            keyGenerator.generateKey()
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw RuntimeException(e)
        } catch (e: CertificateException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

        val cancellationSignal = CancellationSignal()

        if (isPermissionNotGranted()) {
            return
        }

        if (cipherInit() && authCallback != null) {
            cryptoObject = FingerprintManager.CryptoObject(cipher)
            fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, authCallback, null);
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    fun cipherInit(): Boolean {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (e: NoSuchAlgorithmException) {

        } catch (e: NoSuchPaddingException) {
            throw RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            val key = keyStore.getKey (FacebookRequestErrorClassification.KEY_NAME,null) as SecretKey
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (e : KeyPermanentlyInvalidatedException) {
            return false;
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: InvalidAlgorithmParameterException) {
            throw RuntimeException(e)
        } catch (e: CertificateException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }


}