package com.storiqa.storiqawallet.ui.views.countdown

import android.os.Handler
import android.os.Message
import android.os.SystemClock


abstract class CountDownHelper(
        private val mMillisInFuture: Long,
        private val mCountdownInterval: Long
) {

    private var mCancelled = false
    private val mHandler = object : Handler() {

        override fun handleMessage(msg: Message) {

            synchronized(this@CountDownHelper) {
                if (mCancelled) {
                    return
                }

                val millisLeft = mMillisInFuture - SystemClock.elapsedRealtime()

                if (millisLeft <= 0) {
                    onFinish()
                } else if (millisLeft < mCountdownInterval) {
                    // no tick, just delay until done
                    sendMessageDelayed(obtainMessage(UPDATE_TIME), millisLeft)
                } else {
                    val lastTickStart = SystemClock.elapsedRealtime()
                    onTick(millisLeft)

                    // take into account user's onTick taking time to execute
                    var delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime()

                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval

                    sendMessageDelayed(obtainMessage(UPDATE_TIME), delay)
                }

            }
        }
    }

    abstract fun onTick(millisUntilFinished: Long)

    abstract fun onFinish()

    @Synchronized
    fun cancel() {
        mCancelled = true
        mHandler.removeMessages(UPDATE_TIME)
    }

    @Synchronized
    fun start() {
        mCancelled = false
        if (mMillisInFuture <= 0L) {
            onFinish()
        }
        mHandler.sendMessage(mHandler.obtainMessage(UPDATE_TIME))
    }

    companion object {

        val UPDATE_TIME = 0
    }

}