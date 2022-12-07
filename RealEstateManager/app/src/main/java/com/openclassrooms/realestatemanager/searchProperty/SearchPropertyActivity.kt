package com.openclassrooms.realestatemanager.searchProperty

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivitySearchPropertyBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory

class SearchPropertyActivity : AppCompatActivity() {

    //private lateinit var binding: ActivitySearchPropertyBinding

    private val searchPropertyViewModel: SearchPropertyViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding = ActivitySearchPropertyBinding.inflate(layoutInflater)
        //val view = binding.root
        setContentView(R.layout.activity_search_property)

        // Toolbar
        configureToolbar()

    }

    // ------ Toolbar ------
    private fun configureToolbar() {
        val mainActivitytoolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mainActivitytoolbar)
    }

    // Menu Toolbar
    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu is MenuBuilder) menu.setOptionalIconsVisible(true)
        menuInflater.inflate(R.menu.menu_toolbar_search_result,menu)
        return true
    }
}
