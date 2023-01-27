package com.openclassrooms.realestatemanager.searchProperty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.repository.HouseRepository
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.launch

class SearchPropertyViewModel (private val repository: HouseRepository) : ViewModel() {

    val propertiesQuery: LiveData<List<House>>
        get() = _propertiesQuery
    private val _propertiesQuery = MutableLiveData<List<House>>(emptyList())

    // For search property
    fun searchProperty(minPrice:String, maxPrice:String, minNbBathrooms: String, minSurface:String, maxSurface:String, minNbRoom:String,
                       minNbBedroom:String,
                       nearbySchool:Boolean, nearbyBuses: Boolean, nearbyPark: Boolean, nearbyPlayground: Boolean, nearbyShop: Boolean, nearbySubway: Boolean) {
        viewModelScope.launch {
            val propertiesList = repository.getPropertiesQuery(minPrice,maxPrice,minNbBathrooms,minSurface,maxSurface,
                minNbRoom,minNbBedroom,nearbySchool, nearbyBuses, nearbyPark, nearbyPlayground, nearbyShop, nearbySubway)

            _propertiesQuery.postValue(propertiesList)
        }
    }

}