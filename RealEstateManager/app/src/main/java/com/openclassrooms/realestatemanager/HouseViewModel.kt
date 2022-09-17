/*
 * Copyright (c) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openclassrooms.realestatemanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.model.House

class HouseViewModel : ViewModel() {

    private var _currentHouse: MutableLiveData<House> = MutableLiveData<House>()
    val currentHouse: LiveData<House>
        get() = _currentHouse

    private var _maisonsData: ArrayList<House> = ArrayList()
    val maisonsData: ArrayList<House>
        get() = _maisonsData

    init {
        // Initialize the house data.
        //_maisonsData = HouseData.getHouseData()
        _currentHouse.value = _maisonsData[0]
    }

    fun updateCurrentMaison(house: House) {
        _currentHouse.value = house
    }
}
