package com.openclassrooms.realestatemanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.openclassrooms.realestatemanager.model.Agent

@Dao
interface AgentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAgent(agent: Agent)



}