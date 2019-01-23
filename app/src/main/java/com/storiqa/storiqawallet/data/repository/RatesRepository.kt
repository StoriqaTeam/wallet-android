package com.storiqa.storiqawallet.data.repository

import com.storiqa.storiqawallet.data.db.dao.RateDao
import com.storiqa.storiqawallet.data.db.entity.RateEntity
import com.storiqa.storiqawallet.network.CryptoCompareApi
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class RatesRepository(private val rateDao: RateDao,
                      private val cryptoCompareApi: CryptoCompareApi) : IRatesRepository {

    override fun getRates(): Flowable<List<RateEntity>> {
        return rateDao.loadRatesFlowable().subscribeOn(Schedulers.io()).distinct()
    }

    override fun refreshRates(errorHandler: (Exception) -> Unit) {
        cryptoCompareApi.getRates("BTC,ETH,STQ", "USD,RUB")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { errorHandler(it as Exception) }
                .observeOn(Schedulers.io())
                .doOnNext { saveRates(it) }
                .subscribe()
    }

    override fun updateRates(): Observable<HashMap<String, HashMap<String, Double>>> {
        return cryptoCompareApi.getRates("BTC,ETH,STQ", "USD,RUB")
                .doOnNext { saveRates(it) }
                .doOnError { }
    }

    private fun saveRates(rates: HashMap<String, HashMap<String, Double>>) {
        val ratesList = ArrayList<RateEntity>()
        for ((cryptoCurrency, value) in rates)
            for ((fiatCurrency, price) in value)
                ratesList.add(RateEntity(cryptoCurrency, fiatCurrency, price))

        rateDao.deleteAll()
        rateDao.insertAll(ratesList)
    }
}