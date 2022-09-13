package com.openclassrooms.realestatemanager.addproperty

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.repository.HouseRepository
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.launch


class AddHouseViewModel (private val repository: HouseRepository) : ViewModel() {

    val  allHouses: LiveData<List<House>> = repository.allHouses.asLiveData()


    fun insert(house: House) = viewModelScope.launch {
        repository.insert(house)
    }

    fun update(house: House) = viewModelScope.launch {
        repository.update(house)
    }



}