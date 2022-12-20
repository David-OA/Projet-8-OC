package com.openclassrooms.realestatemanager.addagent

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.openclassrooms.realestatemanager.MainActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityAddAgentBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException
import java.util.*

class AddAgentActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddAgentBinding

    private val agentId: String = UUID.randomUUID().toString()

    // For permissions
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    // For context
    private lateinit var context: Context

    // For get data
    private var textFirstNameTextView: String = ""
    private var textLastNameTextView: String = ""
    private var textEmailTextView: String = ""
    private var textPhoneNumberTextView: String = ""

    private val  addAgentViewModel: AddAgentViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAgentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        this.context = this@AddAgentActivity

        configureToolbar()

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
            isWritePermissionGranted = permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted

        }

        requestPermission()

        addAgentPicture()

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
            R.id.menu_add_agent -> checkNoErrorBeforeAddHouse()
        }
        return super.onOptionsItemSelected(item)
    }

    // For check if all is not empty
    private fun checkNoErrorBeforeAddHouse() {
        if (textFirstNameTextView.isEmpty() || textLastNameTextView.isEmpty() || textEmailTextView.isEmpty() || textPhoneNumberTextView.isEmpty()) {
            Toast.makeText(this,"You forgot to fill in a field",Toast.LENGTH_LONG).show()
        } else {
            addAgentInRoomDatabase()
        }
    }

    // For get data
    private fun addAgentInRoomDatabase() {
            // First name
            val editFirstNameTextView = binding.addAgentViewFirstname
            textFirstNameTextView = editFirstNameTextView.text.toString()

            // Last Name
            val editLastNameTextView = binding.addAgentViewLastname
            textLastNameTextView = editLastNameTextView.text.toString()

            // Email
            val editEmailTextView = binding.addAgentViewEmail
            textEmailTextView = editEmailTextView.text.toString()

            // Phone number
            val editPhoneNumberTextView = binding.addAgentViewPhonenb
            textPhoneNumberTextView = editPhoneNumberTextView.text.toString()

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

    private fun addAgentPicture() {
        binding.addAgentViewAddPhoto.setOnClickListener {
            takephoto.launch()
        }
    }

    // For start camera and take a photo
    private val takephoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        lifecycleScope.launch {
            if (isWritePermissionGranted) {
                if (savePhotoToInternalStorage("$agentId", it!!)) {
                    Toast.makeText(this@AddAgentActivity, "Photo Saved Successfully", Toast.LENGTH_LONG).show()

                    Picasso.get()
                        .load(File("/data/data/com.openclassrooms.realestatemanager/files/", "$agentId.jpg"))
                        .transform(CropCircleTransformation())
                        .into(binding.addAgentViewPictureAgent)

                } else {
                    Toast.makeText(this@AddAgentActivity, "Failed to Save photo", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@AddAgentActivity,"Permission not granted", Toast.LENGTH_LONG).show()
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For save pictures pictures
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun savePhotoToInternalStorage(filename: String, bmp: Bitmap) : Boolean {
        return try {
            context.openFileOutput("$filename.jpg", Context.MODE_PRIVATE).use { stream ->
                if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw  IOException("Couldn't save bitamp")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    // For managed request permissions
    private fun requestPermission() {
        val isReadPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val isWritePermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val minSdkLevel = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        isReadPermissionGranted = isReadPermission
        isWritePermissionGranted = isWritePermission || minSdkLevel

        val permissionRequest = mutableListOf<String>()

        if (!isWritePermissionGranted) {
            permissionRequest.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!isReadPermissionGranted) {
            permissionRequest.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        if (permissionRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionRequest.toTypedArray())
        }
    }

}