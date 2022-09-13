package com.openclassrooms.realestatemanager.data.repository

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.data.dao.AgentDao
import com.openclassrooms.realestatemanager.model.Agent
import kotlinx.coroutines.flow.Flow

class AgentRepository(
        private val agentDao: AgentDao
) {

    val allAgent: Flow<List<Agent>> = agentDao.getAllAgent()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(agent: Agent) {
        agentDao.addAgent(agent)
    }

    companion object {
        @Volatile
        private var INSTANCE: AgentRepository? = null
        fun  getAgentRepository(agentDao: AgentDao): AgentRepository {
            return INSTANCE
                ?: synchronized(this) {
                    val instance = AgentRepository(agentDao)
                    INSTANCE = instance
                    return instance
                }
        }
    }
}