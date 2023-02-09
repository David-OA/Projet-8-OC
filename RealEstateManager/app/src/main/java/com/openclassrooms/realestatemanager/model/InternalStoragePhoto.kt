package com.openclassrooms.realestatemanager.model

import android.graphics.Bitmap

data class InternalStoragePhoto(
    val name: String,
    val bmp: Bitmap,
    var description: String
)
