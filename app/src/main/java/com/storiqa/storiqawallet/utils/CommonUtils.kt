package com.storiqa.storiqawallet.utils

import android.os.Build

fun getDeviceOs() = Build.VERSION.SDK_INT.toString()

fun getCurrentTimeMillis() = System.currentTimeMillis().toString()