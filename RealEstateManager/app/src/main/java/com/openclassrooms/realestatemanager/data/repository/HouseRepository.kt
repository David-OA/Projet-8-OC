package com.openclassrooms.realestatemanager.data.repository

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.data.dao.HouseDao
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.flow.Flow
import kotlin.Suppress

class HouseRepository (
        private val houseDao: HouseDao
){

        val allHouses: Flow<List<House>> = houseDao.getAllHouse()

        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(house: House) {
            houseDao.addHouse(house)
        }

        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun update(house: House) {
                houseDao.updateHouse(house)
        }

        companion object {
                @Volatile
                private var INSTANCE: HouseRepository? = null
                fun getHouseRepository(houseDao: HouseDao): HouseRepository {
                        return INSTANCE
                                ?: synchronized(this) {
                                        val instance = HouseRepository(houseDao)
                                        INSTANCE = instance
                                        return instance
                                }
                }
        }
}