package com.openclassrooms.realestatemanager.searchProperty

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.SearchPropertyViewBinding

class SearchPropertyActivity : AppCompatActivity() {

    private lateinit var binding: SearchPropertyViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SearchPropertyViewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
        menuInflater.inflate(R.menu.menu_toolbar_add_property,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_add_property -> startTheSearch()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun startTheSearch() {
        TODO("Not yet implemented")
    }

    private fun getDataForSearch() {

        //Price
        val searchPriceMinTextView = binding.searchViewMinPrice
        val minPrice = searchPriceMinTextView.text.toString()

        val searchPriceMaxTextView = binding.searchViewMaxPrice
        val maxPrice = searchPriceMaxTextView.text.toString()

        //Surface
        val searchSurfaceMinTextView = binding.searchViewMinSurface
        val minSurface = searchSurfaceMinTextView.text.toString()

        val searchSurfaceMaxTextView = binding.searchViewMaxSurface
        val maxSurface = searchSurfaceMaxTextView.text.toString()

        //Room
        val searchRoomMinTextView = binding.searchViewMinRooms
        val textRoomTextView = searchRoomMinTextView.text.toString()

        //Bath
        val searchBathMinTextView = binding.searchViewMinBathrooms
        val textBathTextView = searchBathMinTextView.text.toString()

        //Bed
        val searchBedMinTextView = binding.searchViewMinBedrooms
        val textBedTextView = searchBedMinTextView.text.toString()

    }
}
