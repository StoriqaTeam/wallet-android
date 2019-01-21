package com.storiqa.storiqawallet.data.polling

interface IShortPolling {

    fun start(id: Long, email: String)

    fun stop()

}