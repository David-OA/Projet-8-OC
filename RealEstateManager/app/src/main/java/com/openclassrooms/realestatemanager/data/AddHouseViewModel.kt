package com.openclassrooms.realestatemanager.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.repository.HouseRepository
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.launch


class AddHouseViewModel (private val repository: HouseRepository) : ViewModel() {

    val  allHouses: LiveData<List<House>> = repository.allHouses.asLiveData()


    fun insert(house: House) = viewModelScope.launch {
        repository.insert(house)
    }
}