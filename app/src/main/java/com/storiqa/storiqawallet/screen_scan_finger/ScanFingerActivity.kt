package com.storiqa.storiqawallet.screen_scan_finger

import android.Manifest
import android.annotation.TargetApi
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.storiqa.storiqawallet.R
import java.security.*
import javax.crypto.Cipher
import javax.crypto.NoSuchPaddingException
import android.security.keystore.KeyProperties
import javax.crypto.SecretKey
import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.CancellationSignal
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import javax.crypto.KeyGenerator
import com.facebook.internal.FacebookRequestErrorClassification.KEY_NAME
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.support.annotation.RequiresApi
import java.io.IOException
import java.security.cert.CertificateException


class ScanFingerActivity : AppCompatActivity() {
    // Declare a string variable for the key we’re going to use in our fingerprint authentication

    private lateinit var fingerprintManager: FingerprintManager
    private lateinit var keyguardManager: KeyguardManager

    private lateinit var keyStore: KeyStore
    private lateinit var keyGenerator: KeyGenerator
    private lateinit var cipher: Cipher
    private lateinit var cryptoObject: FingerprintManager.CryptoObject

    @TargetApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_finger);

        keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        fingerprintManager = getSystemService(FINGERPRINT_SERVICE) as FingerprintManager


        if (!keyguardManager.isKeyguardSecure()) {

            Toast.makeText(this,
                    "Lock screen security not enabled in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,
                    "Fingerprint authentication permission not enabled",
                    Toast.LENGTH_LONG).show();

            return;
        }

        if (!fingerprintManager.hasEnrolledFingerprints()) {

            // This happens when no fingerprints are registered.
            Toast.makeText(this,
                    "Register at least one fingerprint in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        generateKey()

        if (cipherInit()) {
            cryptoObject = FingerprintManager.CryptoObject(cipher)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    protected fun generateKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            keyGenerator = KeyGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES,
                    "AndroidKeyStore")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(
                    "Failed to get KeyGenerator instance", e)
        } catch (e: NoSuchProviderException) {
            throw RuntimeException("Failed to get KeyGenerator instance", e)
        }

        try {
            keyStore.load(null)
            keyGenerator.init(KeyGenParameterSpec.Builder(KEY_NAME,
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

        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (cipherInit()) {
            cryptoObject = FingerprintManager.CryptoObject(cipher)
            fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, FingerprintHandler(this), null);

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
            val key = keyStore.getKey (KEY_NAME,null) as SecretKey
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

    @RequiresApi(Build.VERSION_CODES.M)
    inner class FingerprintHandler(private val appContext: Context) : FingerprintManager.AuthenticationCallback() {

        private val cancellationSignal: CancellationSignal? = null


        override fun onAuthenticationError(errMsgId: Int,
                                           errString: CharSequence) {
            Toast.makeText(appContext,
                    "Authentication error\n$errString",
                    Toast.LENGTH_LONG).show()
        }

        override fun onAuthenticationHelp(helpMsgId: Int,
                                          helpString: CharSequence) {
            Toast.makeText(appContext,
                    "Authentication help\n$helpString",
                    Toast.LENGTH_LONG).show()
        }

        override fun onAuthenticationFailed() {
            Toast.makeText(appContext,
                    "Authentication failed.",
                    Toast.LENGTH_LONG).show()
        }

        override fun onAuthenticationSucceeded(
                result: FingerprintManager.AuthenticationResult) {

            Toast.makeText(appContext,
                    "Authentication succeeded.",
                    Toast.LENGTH_LONG).show()
        }
    }
}

