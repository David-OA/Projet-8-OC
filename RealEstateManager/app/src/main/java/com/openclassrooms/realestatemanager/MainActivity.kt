package com.openclassrooms.realestatemanager

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.openclassrooms.realestatemanager.addproperty.AddPropertyFragment
import com.openclassrooms.realestatemanager.maisonlist.MaisonsListFragment

class MainActivity : AppCompatActivity() {

    private var textViewMain: TextView? = null
    private var textViewQuantity: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //binding = ActivityMainBinding.inflate(layoutInflater)
        //val view = binding.root
        //setContentView(view)

        // Toolbar
        val mainActivitytoolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(mainActivitytoolbar)

        //textViewMain = findViewById(R.id.activity_main_activity_text_view_main)
        //textViewQuantity = findViewById(R.id.activity_main_activity_text_view_quantity)
        //configureTextViewMain()
        //configureTextViewQuantity()

        val houseListFragment = MaisonsListFragment()


        if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.sliding_pane_layout, houseListFragment)
                    .commitAllowingStateLoss()
        }

    }




    // CONFIGURE UI

    // ------ Toolbar ------
    private fun configureToolbar() {
        //setSupportActionBar(mainActivitytoolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.add_property->openAddPropertyActivity()
            R.id.search->openSearchPropertyActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun  openAddPropertyActivity() {
        val clickForAddPropertyFragment = AddPropertyFragment.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.recycler_view, clickForAddPropertyFragment, "Add property")
            .commit()
    }

    private fun openSearchPropertyActivity() {
        //val clickForSearchPropertyFragment =
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