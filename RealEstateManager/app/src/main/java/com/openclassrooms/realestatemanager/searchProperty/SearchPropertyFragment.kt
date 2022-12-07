package com.openclassrooms.realestatemanager.searchProperty

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchPropertiesBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory

class SearchPropertyFragment : Fragment() {

    private var _binding: FragmentSearchPropertiesBinding? = null
    private val binding get() = _binding!!

    private var adapterSearchResult: SearchResultAdapter? = null

    // ViewModel
    private val searchPropertyViewModel: SearchPropertyViewModel by activityViewModels {
        ViewModelFactory(Injection.providesHouseRepository(requireContext()), Injection.providesAgentRepository(requireContext()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchPropertiesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireContext(),"ttttrrrrrrrrrrrrrrr",Toast.LENGTH_LONG).show()

    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_start_search -> startTheSearchResultFragment()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startTheSearchResultFragment() {
        getDataForSearch()
        val fragment = SearchResultFragment()
        fragmentManager?.beginTransaction()?.replace(R.id.recyclerView_result_search,fragment)?.commitAllowingStateLoss()
    }

    private fun getDataForSearch() {
        //Price
        val searchPriceMinTextView = binding.searchViewMinPrice
        val minPrice = searchPriceMinTextView.text.toString()

        val searchPriceMaxTextView = binding.searchViewMaxPrice
        val maxPrice = searchPriceMaxTextView.text.toString()

        //Surface
        val searchSurfaceMinTextView = binding.searchViewMinSurface
        val minSurface = searchSurfaceMinTextView.text.toString()

        val searchSurfaceMaxTextView = binding.searchViewMaxSurface
        val maxSurface = searchSurfaceMaxTextView.text.toString()

        //Room
        val searchRoomMinTextView = binding.searchViewMinRooms
        val textRoomTextView = searchRoomMinTextView.text.toString()

        //Bath
        val searchBathMinTextView = binding.searchViewMinBathrooms
        val textBathTextView = searchBathMinTextView.text.toString()

        //Bed
        val searchBedMinTextView = binding.searchViewMinBedrooms
        val textBedTextView = searchBedMinTextView.text.toString()

        searchPropertyViewModel.searchProperty(minPrice,maxPrice,textBathTextView,minSurface,maxSurface,textRoomTextView,textBedTextView)

    }



}