package com.openclassrooms.realestatemanager.searchProperty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.repository.HouseRepository
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

class SearchPropertyViewModel (private val repository: HouseRepository) : ViewModel() {

    /*private lateinit var propertiesQuery: List<House>

    // For search property
    fun searchProperty(minPrice:String, maxPrice:String, minNbBathrooms: String,
                       minSurface:String, maxSurface:String, minNbRoom:String,
                       minNbBedroom:String) {
        viewModelScope.launch {
            propertiesQuery = repository.getPropertiesQuery(minPrice,maxPrice,minNbBathrooms,minSurface,maxSurface,minNbRoom,minNbBedroom)
        }
    }

    // For take the result of the search
    val result: LiveData<List<House>> = liveData {
        propertiesQuery
    }*/

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