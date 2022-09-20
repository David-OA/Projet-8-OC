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

}
