package com.openclassrooms.realestatemanager

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import com.openclassrooms.realestatemanager.addagent.AddAgentActivity
import com.openclassrooms.realestatemanager.addproperty.AddPropertyActivity
import com.openclassrooms.realestatemanager.editproperty.EditPropertyActivity
import com.openclassrooms.realestatemanager.utils.Utils


class MainActivity : AppCompatActivity(){

    private var textViewMain: TextView? = null
    private var textViewQuantity: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        configureToolbar()


        //textViewMain = findViewById(R.id.activity_main_activity_text_view_main)
        //textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity)
        //configureTextViewMain()
        //configureTextViewQuantity()

    }

    // CONFIGURE UI

    // ------ Toolbar ------
    private fun configureToolbar() {
        val mainActivitytoolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mainActivitytoolbar)
    }

    // Menu Toolbar
    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu is MenuBuilder) menu.setOptionalIconsVisible(true)
        menuInflater.inflate(R.menu.menu,menu)
        menu!!.findItem(R.id.edit).isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_property -> openAddPropertyActivity()
            R.id.add_agent -> openAddAgentActivity()
            R.id.edit -> openEditPropertyActivity()
            R.id.search -> openSearchPropertyActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    // For add options
    private fun  openAddPropertyActivity() {
        val clickForAddPropertyActivity = Intent(this, AddPropertyActivity::class.java)
        startActivity(clickForAddPropertyActivity)
    }

    private fun  openAddAgentActivity() {
        val clickForAddAgentActivity = Intent(this, AddAgentActivity::class.java)
        startActivity(clickForAddAgentActivity)
    }

    // For Edit option
    private fun openEditPropertyActivity() {
        val clickForEditPropertyActivity = Intent(this, EditPropertyActivity::class.java)
        startActivity(clickForEditPropertyActivity)
    }

    // For Search option
    private fun openSearchPropertyActivity() {
        val clickForSearchPropertyActivity = SearchPropertyActivity()
        supportFragmentManager.beginTransaction().replace(R.id.recycler_view_list_house, clickForSearchPropertyActivity).commitAllowingStateLoss()
        Toast.makeText(this,"En construction", Toast.LENGTH_LONG).show()
    }

    private fun configureTextViewMain() {
        textViewMain!!.textSize = 15f
        textViewMain!!.text = "Le premier bien immobilier enregistré vaut "
    }

    @SuppressLint("SetTextI18n")
    private fun configureTextViewQuantity() {
        val quantity = Utils.convertDollarToEuro(100)
        textViewQuantity!!.textSize = 20f
        textViewQuantity!!.text = Integer.toString(quantity)
    }

}