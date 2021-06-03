package com.intive.shared

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun String.decodeBase64IntoBitmap(): Bitmap {
    println(this)
    val imageBytes = Base64.decode(this, Base64.DEFAULT)
    println(imageBytes)
    return BitmapFactory.decodeByteArray(
        imageBytes, 0, imageBytes.size)
}