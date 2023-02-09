package com.openclassrooms.realestatemanager.searchProperty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.databinding.FragmentResultSearchBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory

class SearchResultFragment : Fragment() {

    private var _binding: FragmentResultSearchBinding? = null
    private val binding get() = _binding!!

    private var adapterSearchResult: SearchResultAdapter? = null

    // ViewModel
    private val searchResultViewModel: SearchPropertyViewModel by activityViewModels {
        ViewModelFactory(Injection.providesHouseRepository(requireContext()), Injection.providesAgentRepository(requireContext()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentResultSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewSearchResult()
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For manage the recyclerview
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun recyclerViewSearchResult() = binding.recyclerViewResultSearch .apply {

        searchResultViewModel.propertiesQuery.observe(viewLifecycleOwner) {
                houses -> houses?.let {
                    adapterSearchResult = SearchResultAdapter(houses)
                }

            adapter = adapterSearchResult

            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        }
    }

}