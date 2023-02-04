package com.openclassrooms.realestatemanager.injection

import android.content.Context
import com.openclassrooms.realestatemanager.data.database.HouseRoomDatabase
import com.openclassrooms.realestatemanager.data.repository.AgentRepository
import com.openclassrooms.realestatemanager.data.repository.HouseRepository

object Injection {

        fun providesHouseRepository(context: Context): HouseRepository {
            val  database = HouseRoomDatabase.getDatabase(context)
            return HouseRepository.getHouseRepository(database.houseDao())
        }

        fun providesAgentRepository(context: Context): AgentRepository {
            val database = HouseRoomDatabase.getDatabase(context)
            return AgentRepository.getAgentRepository(database.agentDao())
        }

}