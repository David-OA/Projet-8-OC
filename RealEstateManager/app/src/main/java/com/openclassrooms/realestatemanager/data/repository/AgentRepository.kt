package com.openclassrooms.realestatemanager.data.repository

import androidx.annotation.WorkerThread
import com.openclassrooms.realestatemanager.data.dao.AgentDao
import com.openclassrooms.realestatemanager.model.Agent

class AgentRepository(
        private val agentDao: AgentDao
) {

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