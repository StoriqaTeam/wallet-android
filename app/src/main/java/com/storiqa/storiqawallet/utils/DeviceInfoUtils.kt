package com.storiqa.storiqawallet.utils

import android.os.Build

class DeviceInfoUtils {

    companion object {

        fun getDeviceOs() = Build.VERSION.SDK_INT.toString()

        fun getDeviceId() = "09bbda10-2908-4c5a-bd63-9098fc6bffff"

    }
}