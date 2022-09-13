package com.openclassrooms.realestatemanager.addproperty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Agent

class ListAgentDialogAdapter(var agents: List<Agent>) : RecyclerView.Adapter<ListAgentDialogViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAgentDialogViewHolder {
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.list_agent_dialog_item, parent, false)

        return ListAgentDialogViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAgentDialogViewHolder, position: Int) {
        holder.updateWithAgent(agents[position])
    }

    override fun getItemCount(): Int {
        return agents.size
    }

    fun getAgent(position: Int): Agent {
        return agents[position]
    }
}