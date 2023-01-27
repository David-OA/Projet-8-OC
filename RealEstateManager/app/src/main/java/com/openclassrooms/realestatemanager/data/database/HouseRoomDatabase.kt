package com.openclassrooms.realestatemanager.data.database

import android.content.Context
import androidx.room.*
import com.openclassrooms.realestatemanager.data.dao.AgentDao
import com.openclassrooms.realestatemanager.data.dao.HouseDao
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.DescriptionPicturesTypeConverter
import com.openclassrooms.realestatemanager.model.House

//Annotates class to be a Room Database with a table (entity) of the house class
@Database(entities = [House::class, Agent::class],
    version = 1,
    exportSchema = false)
@TypeConverters(DescriptionPicturesTypeConverter::class)
abstract class HouseRoomDatabase: RoomDatabase() {

    // DAO
    abstract fun houseDao(): HouseDao
    abstract fun agentDao(): AgentDao

    companion object {
        @Volatile
        private var INSTANCE: HouseRoomDatabase? = null

        fun getDatabase(context: Context): HouseRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val  instance = Room.databaseBuilder(
                    context.applicationContext,
                    HouseRoomDatabase::class.java,
                    "house_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}