package com.openclassrooms.realestatemanager.addagent

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.databinding.ActivityAddAgentBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent

class AddAgentActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddAgentBinding

    private val  addAgentViewModel: AddAgentViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAgentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        addAgenInRoomDatabase()

    }

    private fun addAgenInRoomDatabase() {
        val fab = binding.fab
        fab.setOnClickListener {

            // First name
            val editFirstNameTextView = binding.addAgentViewFirstname
            val textFirstNameTextView = editFirstNameTextView.text.toString()

            // Last Name
            val editLastNameTextView = binding.addAgentViewLastname
            val textLastNameTextView = editLastNameTextView.text.toString()

            // Email
            val editEmailTextView = binding.addAgentViewEmail
            val textEmailTextView = editEmailTextView.text.toString()

            // Phone number
            val editPhoneNumberTextView = binding.addAgentViewPhonenb
            val textPhoneNumberTextView = editPhoneNumberTextView.text.toString()

            // Creation date
            val editCreationDateTextView = binding

            val agent = Agent("TheBester",
                textFirstNameTextView,
                textLastNameTextView,
                textEmailTextView,
                textPhoneNumberTextView,
                "")

            addAgentViewModel.insert(agent)
        }

    }


}