package com.openclassrooms.realestatemanager.editproperty

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.model.House

class EditPropertyActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddPropertyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        getDataPropertySelectedToEdit()

    }

    private fun getDataPropertySelectedToEdit() {
        val houseIdEdit = intent.extras?.getSerializable("PropertyInFragment") as House

        // Property Id
        val houseId = houseIdEdit.houseId

        //Property type
        binding.addPropertyViewDropdownType.setText(houseIdEdit.detailViewType)

        // Price
        binding.addPropertyViewPrice.setText(houseIdEdit.detailViewPrice)

        // Surface
        binding.addPropertyViewSurface.setText(houseIdEdit.detailsViewSurface)

        // Room
        binding.addPropertyViewRoom.setText(houseIdEdit.detailsViewRooms)

        // Bath
        binding.addPropertyViewBathroom.setText(houseIdEdit.detailsViewBath)

        // Bed
        binding.addPropertyViewBedroom.setText(houseIdEdit.detailsViewBed)

        // Description
        binding.addPropertyViewDescription.setText(houseIdEdit.detailsViewDescription)

        // Address
        binding.addPropertyViewAddress.setText(houseIdEdit.detailsAddress)

        //Neighbourhood
        binding.addPropertyViewNeighbourhood.setText(houseIdEdit.detailViewNearTitle)

        // Near by
        if (houseIdEdit.amenitySubway) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbySubway.isChecked
        }
        if (houseIdEdit.amenitySchool) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbySchool.isChecked
        }
        if (houseIdEdit.amenityShop) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyShop.isChecked
        }
        if (houseIdEdit.amenityPark) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyPark.isChecked
        }
        if (houseIdEdit.amenityPlayground) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyPlayground.isChecked
        }
        if (houseIdEdit.amenityBuses) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyBuses.isChecked
        }

        // On market since
        binding.addPropertyViewSince.setText(houseIdEdit.detailMarketSince)

        // Sold switch
        if (houseIdEdit.detailSold) {
            binding.addPropertyViewSoldSwitch.isChecked
        }

        // Sold on
        binding.addPropertyViewSoldOn.setText(houseIdEdit.detailSoldOn)

        // Agent add property
        binding.addPropertyViewDropdownAgent.setText(houseIdEdit.detailManageBy)
    }
}