package com.storiqa.storiqawallet.di.modules

import com.storiqa.storiqawallet.BuildConfig
import com.storiqa.storiqawallet.data.network.CryptoCompareApi
import com.storiqa.storiqawallet.data.network.OpenWalletApi
import com.storiqa.storiqawallet.data.network.WalletApi
import com.storiqa.storiqawallet.data.network.errors.AuthInterceptor
import com.storiqa.storiqawallet.data.network.errors.ErrorInterceptor
import com.storiqa.storiqawallet.di.scopes.PerApplication
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
class NetworkModule {

    private val cryptoCompareUrl = "https://min-api.cryptocompare.com/"
    private val timeout = 50L

    @Provides
    @PerApplication
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @PerApplication
    internal fun provideWalletApi(okHttpClient: OkHttpClient, authInterceptor: AuthInterceptor): WalletApi {
        val httpClientBuilder = okHttpClient.newBuilder()
                .addInterceptor(authInterceptor)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(WalletApi::class.java)
    }

    @Provides
    @PerApplication
    internal fun provideOpenWalletApi(okHttpClient: OkHttpClient): OpenWalletApi {
        val httpClientBuilder = okHttpClient.newBuilder()
                .addInterceptor(ErrorInterceptor())
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(OpenWalletApi::class.java)
    }

    @Provides
    @PerApplication
    internal fun provideCryptoCompareApi(okHttpClient: OkHttpClient): CryptoCompareApi {
        val httpClientBuilder = okHttpClient.newBuilder()
                .addInterceptor(ErrorInterceptor())
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder()
                .baseUrl(cryptoCompareUrl)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(CryptoCompareApi::class.java)
    }

}