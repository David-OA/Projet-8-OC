package com.openclassrooms.realestatemanager.injection

import android.content.Context
import com.openclassrooms.realestatemanager.data.HouseRoomDatabase
import com.openclassrooms.realestatemanager.data.repository.HouseRepository

object Injection {

        fun providesHouseRepository(context: Context): HouseRepository {
            val  database = HouseRoomDatabase.getDatabase(context)
            return HouseRepository.getHouseRepository(database.houseDao())
        }

        fun providesViewModelFactory(context: Context): ViewModelFactory {
            val  houseRepository = providesHouseRepository(context)

            return ViewModelFactory(houseRepository)
        }

}