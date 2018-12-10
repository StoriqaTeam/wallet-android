package com.storiqa.storiqawallet

import android.app.Application
import android.arch.lifecycle.LifecycleObserver
import android.content.Context
import android.content.res.Resources
import android.support.multidex.MultiDex
import com.storiqa.storiqawallet.di.components.AppComponent
import com.storiqa.storiqawallet.di.components.DaggerAppComponent
import com.storiqa.storiqawallet.di.modules.AppModule
import com.storiqa.storiqawallet.utils.CryptoSignUtils
import javax.inject.Inject

class App : Application(), LifecycleObserver {

    @Inject
    lateinit var signer: CryptoSignUtils

    companion object {
        lateinit var instance: App
            private set

        lateinit var appComponent: AppComponent
            private set

        val res: Resources
            get() = this.instance.resources

        fun getStringFromResources(id: Int): String {
            return this.instance.getString(id)
        }
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        signer = appComponent.cryptoSignUtils()
        /*
        val ks = KeyStore.getInstance(KeyStore.getDefaultType()).apply { load(null) }
        val password = "1234".toCharArray()
        openFileInput("newKeyStoreName").use { fis -> ks.load(fis, password) }
        val protParam = KeyStore.PasswordProtection(password)
        val aliases0: Enumeration<String> = ks.aliases()
        val key0 = TypeConverter.byteArrayToHexString(ks.getKey("secretKeyAlias", password).encoded)

        val mySecretKey = KeyGenerator.getSecretKey()
        val skEntry = KeyStore.SecretKeyEntry(mySecretKey)
        val key1 = TypeConverter.byteArrayToHexString(skEntry.secretKey.encoded)
        ks.setEntry("secretKeyAlias", skEntry, protParam)
        openFileOutput("newKeyStoreName", Context.MODE_PRIVATE).use { fos -> ks.store(fos, password) }
        val aliases: Enumeration<String> = ks.aliases()*/
        val password = "1235".toCharArray()
        //signer.deleteKeyStore(this)
        signer.generateNewKey(password)
        //signer.loadKey(this, password)
        println()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}