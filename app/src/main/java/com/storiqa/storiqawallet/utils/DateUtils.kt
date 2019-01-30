package com.storiqa.storiqawallet.utils

import java.sql.Timestamp

fun getTimastampLong(timestamp: String): Long {
    return Timestamp.valueOf(timestamp.replace("T", " ", true)).time
}