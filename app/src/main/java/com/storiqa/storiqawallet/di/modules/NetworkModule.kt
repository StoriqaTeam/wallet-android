package com.storiqa.storiqawallet.di.modules

import com.storiqa.storiqawallet.BuildConfig
import com.storiqa.storiqawallet.di.scopes.PerApplication
import com.storiqa.storiqawallet.network.CryptoCompareApi
import com.storiqa.storiqawallet.network.WalletApi
import com.storiqa.storiqawallet.network.errors.ErrorInterceptor
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

    @Provides
    @PerApplication
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @PerApplication
    internal fun provideWalletApi(okHttpClient: OkHttpClient): WalletApi {
        val httpClientBuilder = okHttpClient.newBuilder()
                .addInterceptor(ErrorInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)

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
    internal fun provideCryptoCompareApi(okHttpClient: OkHttpClient): CryptoCompareApi {
        val httpClientBuilder = okHttpClient.newBuilder()
                .addInterceptor(ErrorInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)

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