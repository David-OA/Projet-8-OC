package com.openclassrooms.realestatemanager.data.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.flow.Flow

@Dao
interface HouseDao {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun addHouse(house: House)

    @Update
    suspend fun updateHouse(house: House)

    @Query("SELECT * FROM house ")
    fun getAllHouse(): Flow<List<House>>

    @Query("SELECT * FROM house WHERE house.details_price BETWEEN :minPrice AND :maxPrice" +
            " AND house.details_bath >= :minNbBathrooms OR details_bath IS NULL" +
            " AND house.details_surface BETWEEN :minSurface AND :maxSurface" +
            " AND house.details_room >= :minNbRoom" +
            " AND house.details_bed >= :minNbBedroom" )
    suspend fun getPropertiesQuery(
        minPrice:String, maxPrice:String,
        minNbBathrooms: String,
        minSurface:String, maxSurface:String,
        minNbRoom:String,
        minNbBedroom:String): List<House>

}
