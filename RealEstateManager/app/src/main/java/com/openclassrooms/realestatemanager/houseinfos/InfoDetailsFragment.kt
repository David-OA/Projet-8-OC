package com.openclassrooms.realestatemanager.houseinfos

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.addproperty.AddHouseViewModel
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyInfosBinding
import com.openclassrooms.realestatemanager.editproperty.EditPropertyActivity
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.House
import java.io.Serializable

class InfoDetailsFragment : Fragment() {

    private val addHouseViewModel: AddHouseViewModel by activityViewModels{
        ViewModelFactory(Injection.providesHouseRepository(requireContext()), Injection.providesAgentRepository(requireContext()))
    }

    lateinit var pictureRecyclerView: RecyclerView

    private lateinit var houseIdEdit: House

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return FragmentPropertyInfosBinding.inflate(inflater, container, false).root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPropertyInfosBinding.bind(view)

        setHasOptionsMenu(true)


        // For recyclerview
        val picture = ListPicture()

        pictureRecyclerView = binding.detailViewCardPictures
        pictureRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        pictureRecyclerView.adapter = PictureAdapter(picture)


        val args = this.arguments
        if (args != null) {
            val houseId = args.getSerializable("test") as House
            houseIdEdit = houseId


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

    // Menu Toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_toolbar_detail_view_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.edit -> openEditPropertyActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    // For Edit option
    private fun openEditPropertyActivity() {
        val clickForEditPropertyActivity = Intent(requireContext(), EditPropertyActivity::class.java)
        clickForEditPropertyActivity.putExtra("PropertyInFragment", houseIdEdit as Serializable)
        startActivity(clickForEditPropertyActivity)
    }



    fun ListPicture(): Array<Int> {
        val picture = arrayOf(
        R.drawable.photo1,
        R.drawable.photo2,
        R.drawable.photo3,
        R.drawable.photo4,
        R.drawable.photo5,
        R.drawable.photo6,
        R.drawable.photo7,
        R.drawable.photo8,
        R.drawable.photo9,
        R.drawable.photo10)
        return picture
    }

}