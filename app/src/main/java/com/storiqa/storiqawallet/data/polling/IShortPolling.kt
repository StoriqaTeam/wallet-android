package com.storiqa.storiqawallet.data.polling

import io.reactivex.Observable

interface IShortPolling {

    fun start(id: Long, email: String): Observable<Boolean>

}