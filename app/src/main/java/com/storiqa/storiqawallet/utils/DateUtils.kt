package com.storiqa.storiqawallet.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

fun getTimestampLong(timestamp: String): Long {
    return Timestamp.valueOf(timestamp.replace("T", " ", true)).time
}

fun getPresentableTime(timestamp: Long): String {
    val cal = Calendar.getInstance()
    val tz = cal.timeZone

    val sdf = SimpleDateFormat("HH:mm MMM dd")
    sdf.timeZone = tz

    return sdf.format(Date(timestamp))
}