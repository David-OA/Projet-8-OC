package com.openclassrooms.realestatemanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.openclassrooms.realestatemanager.model.Agent
import kotlinx.coroutines.flow.Flow

@Dao
interface AgentDao {

    @Query("SELECT * FROM agent")
    fun getAllAgent(): Flow<List<Agent>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAgent(agent: Agent)

}