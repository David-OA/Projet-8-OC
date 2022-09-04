package com.openclassrooms.realestatemanager.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.openclassrooms.realestatemanager.HouseViewModel
import com.openclassrooms.realestatemanager.data.AddHouseViewModel
import com.openclassrooms.realestatemanager.data.repository.HouseRepository

class ViewModelFactory(
    private val houseRepository: HouseRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AddHouseViewModel::class.java) -> AddHouseViewModel(houseRepository) as T
            modelClass.isAssignableFrom(HouseViewModel::class.java) -> HouseViewModel() as T
            else -> throw Exception("Unknown ViewModel class")
        }
    }
}