package com.openclassrooms.realestatemanager.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.flow.Flow

@Dao
interface HouseDao {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun addHouse(house: House)

    @Query("SELECT * FROM house_database ")
    fun getAllHouse(): Flow<List<House>>

}
