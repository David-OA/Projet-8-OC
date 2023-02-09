package com.openclassrooms.realestatemanager.searchProperty

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentSearchPropertiesBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory

class SearchPropertyFragment : Fragment() {

    private var _binding: FragmentSearchPropertiesBinding? = null
    private val binding get() = _binding!!

    // For checkBoxes near by
    private var playgroundCheck: Boolean = false
    private var schoolCheck: Boolean = false
    private var shopCheck: Boolean = false
    private var subwayCheck: Boolean = false
    private var parkCheck: Boolean = false
    private var busesCheck: Boolean = false

    // For checkBoxes type
    private var flatCheck: Boolean = false
    private var penthouseCheck: Boolean = false
    private var townHouseCheck: Boolean = false
    private var houseCheck: Boolean = false
    private var duplexCheck: Boolean = false

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For get data use in the search
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

        // Near By
        val schoolCheckBox = binding.searchViewAmenitiesNearBy.checkboxNearbySchool
        val playgroundCheckBox = binding.searchViewAmenitiesNearBy.checkboxNearbyPlayground
        val shopCheckBox = binding.searchViewAmenitiesNearBy.checkboxNearbyShop
        val busesCheckBox = binding.searchViewAmenitiesNearBy.checkboxNearbyBuses
        val subwayCheckBox = binding.searchViewAmenitiesNearBy.checkboxNearbySubway
        val parkCheckBox = binding.searchViewAmenitiesNearBy.checkboxNearbyPark

        if (schoolCheckBox.isChecked) {
            schoolCheck = true
        }
        if (playgroundCheckBox.isChecked) {
            playgroundCheck = true
        }
        if (shopCheckBox.isChecked) {
            shopCheck = true
        }
        if (busesCheckBox.isChecked) {
            busesCheck = true
        }
        if (subwayCheckBox.isChecked) {
            subwayCheck = true
        }
        if (parkCheckBox.isChecked) {
            parkCheck = true
        }

        // Type
        val flatCheckBox = binding.searchViewFlatCheck
        val penthouseCheckBox = binding.searchViewPenthouseCheck
        val townHouseCheckBox = binding.searchViewTownhouseCheck
        val houseCheckBox = binding.searchViewHouseCheck
        val duplexCheckBox = binding.searchViewDuplexCheck

        if (flatCheckBox.isChecked) {
            flatCheck = true
        }

        if (penthouseCheckBox.isChecked) {
            penthouseCheck = true
        }

        if (townHouseCheckBox.isChecked) {
            townHouseCheck = true
        }

        if (houseCheckBox.isChecked) {
            houseCheck = true
        }

        if (duplexCheckBox.isChecked) {
            duplexCheck = true
        }

        searchPropertyViewModel.searchProperty(minPrice,maxPrice,textBathTextView,minSurface,maxSurface,textRoomTextView,textBedTextView,
            schoolCheck,playgroundCheck,shopCheck,busesCheck,subwayCheck,parkCheck)

    }
}