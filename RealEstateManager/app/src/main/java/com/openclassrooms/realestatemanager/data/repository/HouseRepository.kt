package com.openclassrooms.realestatemanager.data.repository

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.data.dao.HouseDao
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.flow.Flow
import kotlin.Suppress

class HouseRepository (
        private val houseDao: HouseDao
){

        var allHouses: Flow<List<House>> = houseDao.getAllHouse()

        suspend fun getPropertiesQuery(
                minPrice:String, maxPrice:String, minNbBathrooms: String, minSurface:String, maxSurface:String,
                minNbRoom:String, minNbBedroom:String,
                nearbySchool:Boolean, nearbyBuses: Boolean, nearbyPark: Boolean, nearbyPlayground: Boolean, nearbyShop: Boolean, nearbySubway: Boolean
        ) = houseDao.getPropertiesQuery(minPrice,maxPrice,minNbBathrooms,minSurface,maxSurface,minNbRoom,minNbBedroom,
                nearbySchool, nearbyBuses, nearbyPark, nearbyPlayground, nearbyShop, nearbySubway)

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