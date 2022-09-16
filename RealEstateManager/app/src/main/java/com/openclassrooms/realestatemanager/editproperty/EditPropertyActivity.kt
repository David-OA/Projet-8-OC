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


        val houseIdEdit = intent.extras?.getSerializable("PropertyInFragment") as House

        // Description
        binding.addPropertyViewDescription.setText(houseIdEdit.detailsViewDescription)

        // Address
        binding.addPropertyViewAddress.setText(houseIdEdit.detailsAddress)

        //Neighbourhood
        binding.addPropertyViewNeighbourhood.setText(houseIdEdit.detailViewNearTitle)


    }
}