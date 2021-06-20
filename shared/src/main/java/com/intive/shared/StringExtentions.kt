package com.intive.shared

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun String.decodeBase64IntoBitmap(): Bitmap {
    val imageBytes = Base64.decode(this, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(
        imageBytes, 0, imageBytes.size
    )
}

fun String.getHour() = this.substringAfter("T").take(5)

fun String.swap() = this.substringBefore("T").swapDate()

fun String.swapDate(separator: String = "-"): String {
    val dateElements = this.split(separator)
    return "${dateElements[2]}-${dateElements[1]}-${dateElements[0]}"
}