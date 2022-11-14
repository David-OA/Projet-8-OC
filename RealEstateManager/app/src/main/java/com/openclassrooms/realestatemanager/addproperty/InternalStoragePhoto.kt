package com.openclassrooms.realestatemanager.addproperty

import android.graphics.Bitmap

data class InternalStoragePhoto(
    val name: String,
    val bmp: Bitmap,
    val description:String
)
