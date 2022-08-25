package com.openclassrooms.realestatemanager.maisoninfos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.openclassrooms.realestatemanager.MaisonsViewModel
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentMaisonsInfosBinding
import com.openclassrooms.realestatemanager.model.Maison
import com.smarteist.autoimageslider.SliderView

class InfoDetailsFragment : Fragment() {

    private val maisonsViewModel : MaisonsViewModel by activityViewModels()

    private lateinit var binding: FragmentMaisonsInfosBinding

    lateinit var imageUrl: Array<Int>
    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentMaisonsInfosBinding.inflate(inflater, container, false).root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMaisonsInfosBinding.bind(view)

        sliderView = binding.detailsViewSliderPictures

        val args = this.arguments
        if (args != null) {
            val maison = args.getSerializable("test") as Maison

            binding.detailsViewDescription.text = maison.detailsViewDescription
            binding.detailViewNearTitle.text = maison.detailViewNearTitle
            binding.detailsViewSurface.text = maison.detailsViewSurface
            binding.detailsViewRooms.text = maison.detailsViewRooms
            binding.detailsViewBath.text = maison.detailsViewBath
            binding.detailsViewBed.text = maison.detailsViewBed
            binding.detailViewPrice.text = maison.detailViewPrice
            binding.detailViewType.text = maison.detailViewType
            binding.detailViewNearTitle.text = maison.detailViewNearTitle
            sliderViewForInfoDetailsHome(maison.detailsViewSliderPicture)
        } else {
            maisonsViewModel.currentMaison.observe(this.viewLifecycleOwner) {
                binding.detailsViewDescription.text = it.detailsViewDescription
                binding.detailViewNearTitle.text = it.detailViewNearTitle
                binding.detailsViewSurface.text = it.detailsViewSurface
                binding.detailsViewRooms.text = it.detailsViewRooms
                binding.detailsViewBath.text = it.detailsViewBath
                binding.detailsViewBed.text = it.detailsViewBed
                binding.detailViewPrice.text = it.detailViewPrice
                binding.detailViewType.text = it.detailViewType
                sliderViewForInfoDetailsHome(it.detailsViewSliderPicture)

            }
        }

    }

    private fun  sliderViewForInfoDetailsHome(imageUrl: Array<Int>) {

        sliderAdapter = SliderAdapter(imageUrl)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.sliderAdapter = sliderAdapter
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }
}