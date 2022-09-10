package com.openclassrooms.realestatemanager.houseinfos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.openclassrooms.realestatemanager.HouseViewModel
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.data.AddHouseViewModel
import com.openclassrooms.realestatemanager.databinding.FragmentMaisonsInfosBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.House
import com.smarteist.autoimageslider.SliderView

class InfoDetailsFragment : Fragment() {

    private val houseViewModel : HouseViewModel by activityViewModels()

    private val addHouseViewModel: AddHouseViewModel by activityViewModels{
        ViewModelFactory(Injection.providesHouseRepository(requireContext()))
    }

    lateinit var imageUrl: Array<Int>
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return FragmentMaisonsInfosBinding.inflate(inflater, container, false).root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMaisonsInfosBinding.bind(view)

        sliderView = binding.detailsViewSliderPictures

        val args = this.arguments
        if (args != null) {
            val houseId = args.getSerializable("test") as House


            binding.detailsViewDescription.text = houseId.detailsViewDescription
            binding.detailsViewSurface.text = houseId.detailsViewSurface
            binding.detailsViewRooms.text = houseId.detailsViewRooms
            binding.detailsViewBath.text = houseId.detailsViewBath
            binding.detailsViewBed.text = houseId.detailsViewBed
            binding.detailViewPrice.text = houseId.detailViewPrice
            binding.detailViewType.text = houseId.detailViewType
            if (houseId.amenityBuses == true) {
                binding.detailViewAmenityOne.setImageResource(R.drawable.bus_icon)
            }
            if (houseId.amenityPark == true) {
                binding.detailViewAmenityTwo.setImageResource(R.drawable.park_icon)
            }
            if (houseId.amenityPlayground == true) {
                binding.detailViewAmenityThree.setImageResource(R.drawable.playground_icon)
            }
            if (houseId.amenityShop == true) {
                binding.detailViewAmenityFour.setImageResource(R.drawable.shopping_icon)
            }
            if (houseId.amenitySchool == true) {
                binding.detailViewAmenityFive.setImageResource(R.drawable.school_icon)
            }
            if (houseId.amenitySubway == true) {
                binding.detailViewAmenitySix.setImageResource(R.drawable.subway_icon)
            }
        }
    }

    fun  sliderViewForInfoDetailsHome(imageUrl: Array<Int>) {

        sliderAdapter = SliderAdapter(imageUrl)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.sliderAdapter = sliderAdapter
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }
}