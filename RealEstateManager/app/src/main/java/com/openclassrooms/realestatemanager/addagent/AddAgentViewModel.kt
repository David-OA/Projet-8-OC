package com.openclassrooms.realestatemanager.addagent

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.repository.AgentRepository
import com.openclassrooms.realestatemanager.model.Agent
import kotlinx.coroutines.launch

class AddAgentViewModel (private val agentRepository: AgentRepository) : ViewModel() {

    val getAllAgent: LiveData<List<Agent>> = agentRepository.allAgent.asLiveData()

    fun insert(agent: Agent) = viewModelScope.launch {
        agentRepository.insert(agent)
    }

    // For selected the agent add the house
    val getAgentClick = MutableLiveData<String>()

    fun getNameClicked(agent: Agent) {
        val agentFirstName = agent.firstName
        val agentLastName = agent.lastName

        val nameClicked = "$agentFirstName $agentLastName"
        getAgentClick.postValue(nameClicked)
    }

}