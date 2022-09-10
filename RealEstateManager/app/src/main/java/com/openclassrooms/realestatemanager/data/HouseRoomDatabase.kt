package com.openclassrooms.realestatemanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.data.dao.HouseDao
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Annotates class to be a Room Database with a table (entity) of the house class
@Database(entities = arrayOf(House::class),
    version = 2,
    exportSchema = true)
public abstract class HouseRoomDatabase: RoomDatabase() {

    // DAO
    abstract fun houseDao(): HouseDao

    private class HouseDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    var houseDao = database.houseDao()

                    /*var  addNewHouse = House(12,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "")
                    houseDao.addHouse(addNewHouse)*/

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: HouseRoomDatabase? = null

        fun getDatabase(context: Context): HouseRoomDatabase {
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