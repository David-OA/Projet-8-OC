package com.openclassrooms.realestatemanager.addproperty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.addagent.AddAgentViewModel
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.utils.ItemClickSupport

class ListAgentsDialogView(private val agents: List<Agent>): DialogFragment() {

    private lateinit var viewModel: AddAgentViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ListAgentDialogAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_agents_dialog_view, container, false)
        recyclerView = view.findViewById(R.id.list_agent_dialog_recyclerview)
        configureRecyclerView()
        configureClickRecyclerView()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[AddAgentViewModel::class.java]
    }

    private fun configureRecyclerView(){
        adapter = ListAgentDialogAdapter(agents)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
    }

    private fun configureClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, R.layout.list_agent_dialog_item).setOnItemClickListener { _, position, _ ->
            setAgentSelected(adapter.getAgent(position))
        }
    }

    private fun setAgentSelected(agent: Agent){
        viewModel.getNameClicked(agent)
        dismiss()
    }

}