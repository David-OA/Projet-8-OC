package com.openclassrooms.realestatemanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.FragmentMaisonsListBinding
import com.openclassrooms.realestatemanager.maisonlist.MaisonsListAdapter
import com.openclassrooms.realestatemanager.model.Maison

class MaisonsListFragment : Fragment() {

    private val maisonsViewModel: MaisonsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMaisonsListBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMaisonsListBinding.bind(view)

        // Initialize the adapter and set it to the RecyclerView.
        val adapter = MaisonsListAdapter { maison -> adapterOnClick(maison) }

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter

        maisonsViewModel.currentMaison.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(maisonsViewModel.maisonsData)
            }
        }

    }

    private fun adapterOnClick(maison: Maison) {
        Toast.makeText(context,"test",Toast.LENGTH_LONG).show()
    }


}