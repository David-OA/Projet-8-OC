package com.openclassrooms.realestatemanager.addproperty

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.repository.HouseRepository
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddHouseViewModel (private val repository: HouseRepository) : ViewModel() {

    // For take all houses
    val  allHouses: LiveData<List<House>> = repository.allHouses.asLiveData()


    // For insert house
    fun insert(house: House) = viewModelScope.launch {
        repository.insert(house)
    }

    // For add modification in house
    fun update(house: House) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(house)
    }

}