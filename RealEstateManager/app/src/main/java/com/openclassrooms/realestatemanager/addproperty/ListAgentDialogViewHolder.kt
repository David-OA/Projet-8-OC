package com.openclassrooms.realestatemanager.addproperty

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Agent
import com.squareup.picasso.Picasso

class ListAgentDialogViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @BindView(R.id.list_agent_rv_picture) lateinit var pictureAgent: ImageView
    @BindView(R.id.list_agent_rv_name) lateinit var nameAgent: TextView

    init {
        ButterKnife.bind(this,view)
    }

    fun updateWithAgent(agent: Agent) {
        val nameToFirstName = agent.firstName
        val nameToLastName = agent.lastName

        val nameToDisplay = "$nameToFirstName $nameToLastName"
        nameAgent.text = nameToDisplay

        Picasso
            .get()
            .load(R.drawable.agent_picture_default)
            .into(pictureAgent)
    }


}