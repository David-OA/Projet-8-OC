package com.openclassrooms.realestatemanager.addproperty


import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.addagent.AddAgentViewModel
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.utils.TypeProperty


class AddPropertyActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddPropertyBinding

    private var playgroudCheck: Boolean = false
    private var schoolCheck: Boolean = false
    private var shopCheck: Boolean = false
    private var subwayCheck: Boolean = false
    private var parkCheck: Boolean = false
    private var busesCheck: Boolean = false

    private var switchSoldCheck: Boolean = false

    private val addHouseViewModel: AddHouseViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    private val addAgentViewModel: AddAgentViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    private val dropdownTypeHouse by lazy { binding.addPropertyViewDropdownType }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        addHouseInRoomDatabase()

        getHouseType()

        getAgentInTheList()

        showAgentListSelected()

    }

    private fun getHouseType() {
        //Type House
        val houseType = mutableListOf<String>()
        TypeProperty.values().forEach {
            houseType.add(it.typeName)
        }

        ArrayAdapter(this, android.R.layout.simple_spinner_item, houseType).also {
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
    private fun addHouseInRoomDatabase() {
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

            val house = House(2,
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

            addHouseViewModel.insert(house)
        }
    }
}