package com.openclassrooms.realestatemanager.utils

import java.text.NumberFormat
import java.util.*


    fun Double.toEuroDisplay(): String {
        return NumberFormat.getNumberInstance(Locale.FRANCE).format(this)
    }

    var idGenerated: String = ""
    get() {
        field = UUID.randomUUID().toString()
        return field
    }
