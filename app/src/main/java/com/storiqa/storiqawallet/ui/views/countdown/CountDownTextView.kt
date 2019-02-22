package com.storiqa.storiqawallet.ui.views.countdown

import android.content.Context
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import java.util.*


class CountDownTextView : TextView {
    var countDownInterval: Long = 1000

    private var scheduledTime: Long = 0
    private var isAutoShowText: Boolean = true
    private var countDownCallback: CountDownCallback? = null
    private var mCountDownHelper: CountDownHelper? = null
    private var mVisible: Boolean = false
    private var mStarted: Boolean = false
    private var mRunning: Boolean = false
    private var mLogged: Boolean = false
    private var mFormat: String? = null
    private var mFormatter: Formatter? = null
    private var mFormatterLocale: Locale? = null
    private val mFormatterArgs = arrayOfNulls<Any>(1)
    private var mFormatBuilder: StringBuilder? = null
    private var mTimeFlag: Int = TIME_SHOW_M_S
    private val mRecycle = StringBuilder(12)

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setTimeFormat(TIME_SHOW_H_M_S)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        updateRunning()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mVisible = false
        updateRunning()
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        mVisible = visibility == View.VISIBLE
        updateRunning()
    }

    private fun updateRunning() {
        val running = mVisible && mStarted
        if (running != mRunning) {
            if (running) {
                mCountDownHelper!!.start()
            } else {
                mCountDownHelper!!.cancel()
            }
            mRunning = running
        }
    }

    fun start() {
        startCountDown()
        mStarted = true
        updateRunning()

    }

    fun cancel() {
        mStarted = false
        updateRunning()
    }

    fun setAutoDisplayText(isAutoShowText: Boolean) {
        this.isAutoShowText = isAutoShowText
    }

    fun addOnFinishCallback(onFinish: () -> Unit) {
        countDownCallback = object : CountDownCallback {
            override fun onTick(countDownTextView: CountDownTextView, millisUntilFinished: Long) {

            }

            override fun onFinish(countDownTextView: CountDownTextView) {
                onFinish.invoke()
            }
        }
    }

    interface CountDownCallback {

        fun onTick(countDownTextView: CountDownTextView, millisUntilFinished: Long)

        fun onFinish(countDownTextView: CountDownTextView)

    }

    fun setFormat(format: String?) {
        mFormat = format
        if (format != null && mFormatBuilder == null) {
            mFormatBuilder = StringBuilder(format.length * 2)
        }
    }

    private fun getFormatTime(now: Long): String {
        val day = ElapsedTimeUtil.MILLISECONDS.toDays(now)
        val hour = ElapsedTimeUtil.MILLISECONDS.toHours(now)
        val minute = ElapsedTimeUtil.MILLISECONDS.toMinutes(now)
        val seconds = ElapsedTimeUtil.MILLISECONDS.toSeconds(now)

        mRecycle.setLength(0)
        val f = Formatter(mRecycle, Locale.getDefault())
        val text: String
        when (mTimeFlag) {
            TIME_SHOW_D_H_M_S -> text = f.format(TIME_FORMAT_D_H_M_S, day, hour, minute, seconds).toString()
            TIME_SHOW_H_M_S -> text = f.format(TIME_FORMAT_H_M_S, hour, minute, seconds).toString()

            TIME_SHOW_M_S -> text = f.format(TIME_FORMAT_M_S, minute, seconds).toString()

            TIME_SHOW_S -> text = f.format(TIME_FORMAT_S, seconds).toString()
            else -> text = f.format(TIME_FORMAT_H_M_S, seconds).toString()
        }

        return text
    }


    @Synchronized
    private fun updateText(now: Long) {
        var text = getFormatTime(now)

        if (mFormat != null) {
            val loc = Locale.getDefault()
            if (mFormatter == null || !loc.equals(mFormatterLocale)) {
                mFormatterLocale = loc
                mFormatter = Formatter(mFormatBuilder, loc)
            }
            mFormatBuilder!!.setLength(0)
            mFormatterArgs[0] = text
            try {
                mFormatter!!.format(mFormat, mFormatterArgs)
                text = mFormatBuilder!!.toString()
            } catch (ex: IllegalFormatException) {
                if (!mLogged) {
                    mLogged = true
                }
            }

        }
        setText(text)
    }

    fun setCountdownTime(millisInFuture: Long) {
        if (millisInFuture <= 0)
            return

        cancel()
        scheduledTime = SystemClock.elapsedRealtime() + millisInFuture
        start()
    }

    fun addCountDownCallback(callback: CountDownCallback) {
        countDownCallback = callback
    }

    fun setTimeFormat(timeFlag: Int) {
        //  this.mTimeFormat = mTimeFormat;
        mTimeFlag = timeFlag
    }

    private fun startCountDown() {

        mCountDownHelper = object : CountDownHelper(scheduledTime, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                if (isAutoShowText) {
                    updateText(millisUntilFinished)
                }
                if (countDownCallback != null) {
                    countDownCallback!!.onTick(this@CountDownTextView, millisUntilFinished)
                }
            }

            override fun onFinish() {
                if (countDownCallback != null) {
                    countDownCallback!!.onFinish(this@CountDownTextView)
                }
            }
        }
        mCountDownHelper!!.start()

    }

    override fun onInitializeAccessibilityEvent(event: AccessibilityEvent) {
        super.onInitializeAccessibilityEvent(event)
        event.className = CountDownTextView::class.java.name
    }

    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)
        info.className = CountDownTextView::class.java.name
    }

    companion object {

        private val TAG = "CountDownTextView"

        val TIME_SHOW_D_H_M_S = 10
        val TIME_SHOW_H_M_S = 20
        val TIME_SHOW_M_S = 30
        val TIME_SHOW_S = 40

        private val TIME_FORMAT_D_H_M_S = "%1$02d:%2$02d:%3$02d:%4$02d"
        private val TIME_FORMAT_H_M_S = "%1$02d:%2$02d:%3$02d"
        private val TIME_FORMAT_M_S = "%1$02d:%2$02d"
        private val TIME_FORMAT_S = "%1$02d"
    }
}