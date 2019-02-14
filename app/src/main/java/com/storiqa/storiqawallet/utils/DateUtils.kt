package com.storiqa.storiqawallet.utils

import com.storiqa.storiqawallet.App
import com.storiqa.storiqawallet.R
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun getTimestampLong(timestamp: String): Long {
    return Timestamp.valueOf(timestamp.replace("T", " ", true)).time
}

fun getPresentableDate(timestamp: Long): String {
    val cal = Calendar.getInstance()
    val tz = cal.timeZone

    val sdf = SimpleDateFormat("HH:mm MMM dd")
    sdf.timeZone = tz

    return sdf.format(Date(timestamp))
}

fun getPresentableTime(t: Int): String {
    val hours = t / 3600
    val minutes = (t / 60) % 60
    val seconds = t % 60
    val time = StringBuilder()
    if (hours > 0)
        time.append(hours).append(App.res.getString(R.string.text_hours))
    if (hours > 0 || minutes > 0)
        time.append(minutes).append(App.res.getString(R.string.text_minutes))
    time.append(seconds).append(App.res.getString(R.string.text_seconds))
    return time.toString()
}