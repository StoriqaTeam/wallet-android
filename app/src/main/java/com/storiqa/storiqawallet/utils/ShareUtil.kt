package com.storiqa.storiqawallet.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.facebook.FacebookSdk
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

fun shareImage(context: Context, image: Bitmap) {
    val imagesFolder = File(FacebookSdk.getCacheDir(), "images")
    var uri: Uri? = null
    try {
        imagesFolder.mkdirs()
        val file = File(imagesFolder, "shared_image.png")

        val stream = FileOutputStream(file)
        image.compress(Bitmap.CompressFormat.PNG, 90, stream)
        stream.flush()
        stream.close()
        uri = FileProvider.getUriForFile(context, "com.mydomain.fileprovider", file)

    } catch (e: IOException) {
        Log.d("TAGGG", "IOException while trying to write file for sharing: " + e.message)
    }

    val intent = Intent(Intent.ACTION_SEND)
    intent.putExtra(Intent.EXTRA_STREAM, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.type = "image/png"
    context.startActivity(intent)
}