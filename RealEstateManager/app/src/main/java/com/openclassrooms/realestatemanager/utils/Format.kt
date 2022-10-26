package com.openclassrooms.realestatemanager.utils

import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToLong


    fun Double.toEuroDisplay(): String {
        return NumberFormat.getNumberInstance(Locale.FRANCE).format(this)
    }

    fun Double.toDollarDisplay(): String{
        return NumberFormat.getNumberInstance(Locale.US).format(this)
    }

    var idGeneratedProperty: String = ""
    get() {
        field = UUID.randomUUID().toString()
        return field
    }

    fun Double.toEuro(currency: Currency): Double{
        if(currency == Currency.EURO) return this

        return (this * 0.812).roundToLong().toDouble()
    }

    fun Double.toDollar():Double{
        return (this * 1.137).roundToLong().toDouble()
    }


