package com.openclassrooms.realestatemanager.addagent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.repository.AgentRepository
import com.openclassrooms.realestatemanager.model.Agent
import kotlinx.coroutines.launch

class AddAgentViewModel (private val agentRepository: AgentRepository) : ViewModel() {

    fun insert(agent: Agent) = viewModelScope.launch {
        agentRepository.insert(agent)
    }
}