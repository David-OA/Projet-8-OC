package com.openclassrooms.realestatemanager.editproperty

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.MainActivity
import com.openclassrooms.realestatemanager.addagent.AddAgentViewModel
import com.openclassrooms.realestatemanager.addproperty.AddHouseViewModel
import com.openclassrooms.realestatemanager.addproperty.ListAgentsDialogView
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.utils.TypeProperty
import com.openclassrooms.realestatemanager.utils.idGenerated

class EditPropertyActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddPropertyBinding

    private var playgroudCheck: Boolean = false
    private var schoolCheck: Boolean = false
    private var shopCheck: Boolean = false
    private var subwayCheck: Boolean = false
    private var parkCheck: Boolean = false
    private var busesCheck: Boolean = false

    private var switchSoldCheck: Boolean = false

    private lateinit var houseIdUpdate: String

    private val addHouseViewModel: AddHouseViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    private val addAgentViewModel: AddAgentViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    private val dropdownTypeHouse by lazy { binding.addPropertyViewDropdownType }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        
        // For get data
        getDataPropertySelectedToEdit()

        // For edit change
        getHouseType()
        getAgentInTheList()
        addHouseInRoomDatabaseAfterChange()

    }

    private fun getDataPropertySelectedToEdit() {
        val houseIdEdit = intent.extras?.getSerializable("PropertyInFragment") as House

        // Property Id
        houseIdUpdate = houseIdEdit.houseId

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
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbySubway.isChecked = true
        }
        if (houseIdEdit.amenitySchool) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbySchool.isChecked = true
        }
        if (houseIdEdit.amenityShop) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyShop.isChecked = true
        }
        if (houseIdEdit.amenityPark) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyPark.isChecked = true
        }
        if (houseIdEdit.amenityPlayground) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyPlayground.isChecked = true
        }
        if (houseIdEdit.amenityBuses) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyBuses.isChecked = true
        }

        // On market since
        binding.addPropertyViewSince.setText(houseIdEdit.detailMarketSince)

        // Sold switch
        if (houseIdEdit.detailSold) {
            binding.addPropertyViewSoldSwitch.isChecked = true
        }

        // Sold on
        binding.addPropertyViewSoldOn.setText(houseIdEdit.detailSoldOn)

        // Agent add property
        binding.addPropertyViewDropdownAgent.setText(houseIdEdit.detailManageBy)
    }

    // For Save the data after change
    private fun getHouseType() {
        //Type House
        val houseType = mutableListOf<String>()
        TypeProperty.values().forEach {
            houseType.add(it.typeName)
        }

        ArrayAdapter(this, R.layout.simple_spinner_item, houseType).also {
                adapter -> adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            dropdownTypeHouse.setAdapter(adapter)
        }
    }

    private fun getAgentInTheList() {
        val clickForChoiceAgentInList = binding.addPropertyViewDropdownAgent
        addAgentViewModel.getAllAgent.observe(this) {
            clickForChoiceAgentInList.setOnClickListener{
                val listAgent = addAgentViewModel.getAllAgent.value
                val dialog = ListAgentsDialogView(listAgent!!)
                dialog.show(supportFragmentManager, "tag test")
            }
        }

    }

    private fun showAgentListSelected() {
        val dropdownAgentAddHouse = binding.addPropertyViewDropdownAgent
        val agentSelected = addAgentViewModel.getAgentClick.value.toString()
        dropdownAgentAddHouse.setText(agentSelected)
    }


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun addHouseInRoomDatabaseAfterChange() {
        val fab = binding.fab
        fab.setOnClickListener{

            //Price
            val editPriceTextView = binding.addPropertyViewPrice
            val textPriceTextView = editPriceTextView.text.toString()

            //Surface
            val editSurfaceTextView = binding.addPropertyViewSurface
            val textSurfaceTextView = editSurfaceTextView.text.toString()

            //Room
            val editRoomTextView = binding.addPropertyViewRoom
            val textRoomTextView = editRoomTextView.text.toString()

            //Bath
            val editBathTextView = binding.addPropertyViewBathroom
            val textBathTextView = editBathTextView.text.toString()

            //Bed
            val editBedTextView = binding.addPropertyViewBedroom
            val textBedTextView = editBedTextView.text.toString()

            //Description
            val editDescriptionTextView = binding.addPropertyViewDescription
            val textDescriptionTextView = editDescriptionTextView.text.toString()

            //Address
            val editAdressTextView = binding.addPropertyViewAddress
            val textAdressTextView = editAdressTextView.text.toString()

            //Neighbourhood
            val editNeighbourhoodTextView = binding.addPropertyViewNeighbourhood
            val textNeighbourhoodTextView = editNeighbourhoodTextView.text.toString()

            // Near By
            val schoolCheckBoxe = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbySchool
            val playgroudCheckBoxe = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyPlayground
            val shopCheckBoxe = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyShop
            val busesCheckBoxe = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyBuses
            val subwayCheckBoxe = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbySubway
            val parkCheckBoxe = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyPark

            if (schoolCheckBoxe.isChecked) {
                schoolCheck = true
            }
            if (playgroudCheckBoxe.isChecked) {
                playgroudCheck = true
            }
            if (shopCheckBoxe.isChecked) {
                shopCheck = true
            }
            if (busesCheckBoxe.isChecked) {
                busesCheck = true
            }
            if (subwayCheckBoxe.isChecked) {
                subwayCheck = true
            }
            if (parkCheckBoxe.isChecked) {
                parkCheck = true
            }

            val  typeHouseChoice = dropdownTypeHouse.text.toString()

            //On market since
            val onMarketSince = binding.addPropertyViewSince
            val textOnMarketSince = onMarketSince.text.toString()


            //Sold
            val switchSold = binding.addPropertyViewSoldSwitch
            if (switchSold.isChecked) {
                switchSoldCheck = true
            }

            //Sold On
            val soldOn = binding.addPropertyViewSoldOn
            val textSoldOn = soldOn.text.toString()

            // Agent add house
            val textAgentAddHouse = addAgentViewModel.getAgentClick.value.toString()


            val house = House(houseIdUpdate,
                typeHouseChoice,
                textPriceTextView,
                textSurfaceTextView,
                textRoomTextView,
                textBedTextView,
                textBathTextView,
                busesCheck,
                schoolCheck,
                playgroudCheck,
                shopCheck,
                subwayCheck,
                parkCheck,
                textDescriptionTextView,
                textAdressTextView,
                textNeighbourhoodTextView,
                textOnMarketSince,
                switchSoldCheck,
                textSoldOn,
                textAgentAddHouse)

            addHouseViewModel.update(house)

            returnToMainActivity()
            showToastForUpdateHouse()
        }

    }

    private fun  returnToMainActivity() {
        val mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
    }

    private fun showToastForUpdateHouse() {
        Toast.makeText(this, "Les modifications ont bien été enregistrée", Toast.LENGTH_LONG).show()
    }
}