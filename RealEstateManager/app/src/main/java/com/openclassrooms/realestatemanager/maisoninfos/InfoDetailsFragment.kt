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

        maisonsViewModel.currentMaison.observe(this.viewLifecycleOwner) {
            binding.detailsViewDescription.text = it.detailsViewDescription
            binding.detailViewNearTitle.text = it.detailViewNearTitle
            binding.detailsViewSurface.text = it.detailsViewSurface
            binding.detailsViewRooms.text = it.detailsViewRooms
            binding.detailsViewBath.text = it.detailsViewBath
            binding.detailsViewBed.text = it.detailsViewBed
            binding.detailViewPrice.text = it.detailViewPrice
            binding.detailViewType.text = it.detailViewType
            binding.detailViewNearTitle.text = it.detailViewNearTitle
            
        }

        sliderViewForInfoDetailsHome()
    }

    private fun  sliderViewForInfoDetailsHome() {

        imageUrl = arrayOf(
            R.drawable.photo1,
            R.drawable.photo2,
            R.drawable.photo3,
            R.drawable.photo4,
            R.drawable.photo5,
            R.drawable.photo6,
            R.drawable.photo7,
            R.drawable.photo8,
            R.drawable.photo9,
            R.drawable.photo10
        )

        sliderAdapter = SliderAdapter(imageUrl)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.sliderAdapter = sliderAdapter
        sliderView.scrollTimeInSec = 3
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()
    }
}