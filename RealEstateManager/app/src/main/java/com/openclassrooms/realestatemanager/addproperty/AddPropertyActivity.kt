package com.openclassrooms.realestatemanager.addproperty


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.openclassrooms.realestatemanager.MainActivity
import com.openclassrooms.realestatemanager.addagent.AddAgentViewModel
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.utils.TypeProperty
import com.openclassrooms.realestatemanager.utils.idGenerated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*


class AddPropertyActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddPropertyBinding

    private var playgroudCheck: Boolean = false
    private var schoolCheck: Boolean = false
    private var shopCheck: Boolean = false
    private var subwayCheck: Boolean = false
    private var parkCheck: Boolean = false
    private var busesCheck: Boolean = false

    private var switchSoldCheck: Boolean = false

    private var propertyId: String = idGenerated

    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var context: Context

    private lateinit var internalStoragePhotoAdapter: InternalStoragePhotoAdapter

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

        this.internalStoragePhotoAdapter = InternalStoragePhotoAdapter { }

        this.context = this@AddPropertyActivity

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
            isWritePermissionGranted = permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted

        }

        requestPermission()

        getHouseType()

        getAgentInTheList()

        addHouseInRoomDatabase()

        val takephoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            lifecycleScope.launch {
                if (isWritePermissionGranted) {

                    if (savePhotoToInternalStorage(propertyId + "." + UUID.randomUUID().toString(), it!!)) {
                        Toast.makeText(this@AddPropertyActivity, "Photo Saved Successfully", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@AddPropertyActivity, "Failed to Save photo", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@AddPropertyActivity,"Permission not granted", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.addPropertyViewAddPictureButton.setOnClickListener {
            takephoto.launch()

        }

        binding.addPictureInRecyclerview.setOnClickListener {
            setupInternalStorageRecyclerView()
            loadPhotosFromInternalStorageIntoRecyclerView()
        }

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
        val agentSelected = addAgentViewModel.getAgentClick.value.toString()
        binding.addPropertyViewDropdownAgent.setText(agentSelected)
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

            // type Property
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

            val house = House(propertyId,
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

            returnToMainActivity()
            showToastForAddHouse()
        }
    }

    private fun  returnToMainActivity() {
        val mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
    }

    private fun showToastForAddHouse() {
        Toast.makeText(this, "La propiété à bien été ajouté", Toast.LENGTH_LONG).show()
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

    private fun takePhoto() {
        val takephoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            lifecycleScope.launch {
                if (isWritePermissionGranted) {

                    if (savePhotoToInternalStorage(propertyId + "." + UUID.randomUUID().toString(), it!!)) {
                        Toast.makeText(this@AddPropertyActivity, "Photo Saved Successfully", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@AddPropertyActivity, "Failed to Save photo", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@AddPropertyActivity,"Permission not granted", Toast.LENGTH_LONG).show()
                }
            }
        }
        takephoto.launch()
    }

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

    private fun setupInternalStorageRecyclerView() = binding.addPropertyViewPictureRv.apply {
        adapter = internalStoragePhotoAdapter
        layoutManager = LinearLayoutManager(this@AddPropertyActivity, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
        lifecycleScope.launch {
            val photos = loadPhotosFromInternalStorage()
            internalStoragePhotoAdapter.submitList(photos)
        }
    }

    private suspend fun loadPhotosFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files = filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") && it.name.startsWith(propertyId) }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bmp)
            } ?: listOf()
        }
    }
}