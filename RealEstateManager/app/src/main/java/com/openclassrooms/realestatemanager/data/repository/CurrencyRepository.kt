package com.openclassrooms.realestatemanager.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.utils.Currency

class CurrencyRepository(context: Context) {

    var KEY_PREF = "test"
    var KEY_PREF_CURRENCY = "test"

    private val sharedPreferences = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)

    private val currencyLD = MutableLiveData<Currency>()
    val currency: LiveData<Currency>
        get() {
            if (currencyLD.value == null) currencyLD.value = getCurrencyFromPrefs()
            return  currencyLD
        }

    fun setCurrency() {
        currencyLD.value = when(currencyLD.value) {
            Currency.EURO -> Currency.DOLLAR
            Currency.DOLLAR -> Currency.EURO
            else -> Currency.EURO
        }
        sharedPreferences?.let {
            val editorCurrencySharedPr = sharedPreferences.edit()
            editorCurrencySharedPr.putString(KEY_PREF_CURRENCY, currency.value?.nameCurrency)
            editorCurrencySharedPr.apply()
        }
    }

    private fun getCurrencyFromPrefs(): Currency{
        sharedPreferences?.getString(KEY_PREF_CURRENCY, Currency.EURO.nameCurrency)?.let{
            return Currency.valueOf(it)
        }
        return Currency.EURO
    }

    companion object{
        @Volatile
        private var INSTANCE: CurrencyRepository? = null
        fun getCurrencyRepository(context: Context): CurrencyRepository {
            return INSTANCE
                ?: synchronized(this){
                    val instance = CurrencyRepository(context)
                    INSTANCE = instance
                    return instance
                }
        }
    }
}