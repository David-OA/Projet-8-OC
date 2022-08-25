package com.openclassrooms.realestatemanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.openclassrooms.realestatemanager.model.Maison
import kotlinx.coroutines.CoroutineScope

// Annotates class to be a Room Database with a table (entity) of the house class
@Database(entities = arrayOf(Maison::class),
    version = 1,
    exportSchema = false)
public abstract class HouseRoomDatabase: RoomDatabase() {

    abstract fun houseDao(): MaisonDao

    companion object {
        @Volatile
        private var INSTANCE: HouseRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): HouseRoomDatabase {
            return INSTANCE?: synchronized(this) {
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