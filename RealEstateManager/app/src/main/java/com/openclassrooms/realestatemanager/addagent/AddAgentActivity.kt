package com.openclassrooms.realestatemanager.addagent

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import com.openclassrooms.realestatemanager.MainActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityAddAgentBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import java.util.*

class AddAgentActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddAgentBinding

    private val agentId: String = UUID.randomUUID().toString()

    private val  addAgentViewModel: AddAgentViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAgentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureToolbar()

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Toolbar
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ------ Toolbar ------
    private fun configureToolbar() {
        val addAgentActivitytoolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(addAgentActivitytoolbar)
        title = "Add a Agent"
    }

    // Menu Toolbar
    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu is MenuBuilder) menu.setOptionalIconsVisible(true)
        menuInflater.inflate(R.menu.menu_toolbar_add_agent,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_add_agent -> addAgenInRoomDatabase()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addAgenInRoomDatabase() {

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

            val agent = Agent(agentId,
                textFirstNameTextView,
                textLastNameTextView,
                textEmailTextView,
                textPhoneNumberTextView,
                "")

            addAgentViewModel.insert(agent)

            returnToMainActivity()
            showToastForAddAgent()
    }

    private fun  returnToMainActivity() {
        val mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
    }

    private fun showToastForAddAgent() {
        Toast.makeText(this, "L'agent' à bien été ajouté", Toast.LENGTH_LONG).show()
    }


}