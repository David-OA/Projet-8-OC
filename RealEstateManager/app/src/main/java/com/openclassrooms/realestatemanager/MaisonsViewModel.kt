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
import com.openclassrooms.realestatemanager.data.HouseData
import com.openclassrooms.realestatemanager.model.Maison

class MaisonsViewModel : ViewModel() {

    private var _currentMaison: MutableLiveData<Maison> = MutableLiveData<Maison>()
    val currentMaison: LiveData<Maison>
        get() = _currentMaison

    private var _maisonsData: ArrayList<Maison> = ArrayList()
    val maisonsData: ArrayList<Maison>
        get() = _maisonsData

    init {
        // Initialize the house data.
        _maisonsData = HouseData.getHouseData()
        _currentMaison.value = _maisonsData[0]
    }

    fun updateCurrentMaison(maison: Maison) {
        _currentMaison.value = maison
    }
}
