package com.openclassrooms.realestatemanager.editproperty

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.PopupMenu
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.MainActivity
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.addagent.AddAgentViewModel
import com.openclassrooms.realestatemanager.addproperty.AddHouseViewModel
import com.openclassrooms.realestatemanager.addproperty.InternalStoragePhoto
import com.openclassrooms.realestatemanager.addproperty.ListPictureDescriptionAdapter
import com.openclassrooms.realestatemanager.addproperty.ListAgentsDialogView
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.DescriptionPictures
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.utils.TypeProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

class EditPropertyActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddPropertyBinding

    private var playgroudCheck: Boolean = false
    private var schoolCheck: Boolean = false
    private var shopCheck: Boolean = false
    private var subwayCheck: Boolean = false
    private var parkCheck: Boolean = false
    private var busesCheck: Boolean = false

    private var switchSoldCheck: Boolean = false

    private lateinit var houseIdUpdate: String
    private var picturesId: String = ""
        get() {
            field = UUID.randomUUID().toString()
            return field
        }

    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var context: Context

    private lateinit var listPictureDescriptionEditAdapter: ListPictureDescriptionEditAdapter

    // For show description in recyclerView
    private var lisDescriptionPicture: List<DescriptionPictures> = listOf()

    private val addHouseViewModel: AddHouseViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    private val addAgentViewModel: AddAgentViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    private val dropdownTypeHouse by lazy { binding.addPropertyViewDropdownType }

    private var descriptionPictureList: MutableList<DescriptionPictures> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        configureToolbar()

        this.context = this@EditPropertyActivity

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
            isWritePermissionGranted = permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted

        }
        
        // For get data and show
        getDataPropertySelectedToEdit()
        this.listPictureDescriptionEditAdapter = ListPictureDescriptionEditAdapter (descriptionPictureList, lisDescriptionPicture.toMutableList())



        setUpRecyclerviewPictures()

        // For edit change
        getHouseType()
        getAgentInTheList()

        binding.addPictureInRecyclerview.setOnClickListener {

        }

        choiceHowTakeAPicture()

    }

    private fun choiceHowTakeAPicture() {
        binding.addPropertyViewAddPictureButton.setOnClickListener {
            showPopup(binding.addPropertyViewAddPictureButton)
        }
    }

    // For start camera and take a photo
    private val takephoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        lifecycleScope.launch {
            if (isWritePermissionGranted) {
                if (savePhotoToInternalStorage("$houseIdUpdate.$picturesId", it!!)) {
                    Toast.makeText(this@EditPropertyActivity, "Photo Saved Successfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@EditPropertyActivity, "Failed to Save photo", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@EditPropertyActivity,"Permission not granted", Toast.LENGTH_LONG).show()
            }
        }
    }

    // For take a photo from Media
    private val pickPhoto = registerForActivityResult(ActivityResultContracts.GetContent()) {
        lifecycleScope.launch {
            if (isWritePermissionGranted) {
                val imageUrl: Uri = it!!
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver,imageUrl)
                if (savePhotoToInternalStorage("$houseIdUpdate.$picturesId", bitmap/*it!!*/)) {
                    Toast.makeText(this@EditPropertyActivity, "Photo Saved Successfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@EditPropertyActivity, "Failed to Save photo", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@EditPropertyActivity,"Permission not granted", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun selectPictureInMedia() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            pickPhoto.launch("image/*")
        } else {
            Toast.makeText(this, "Read Permission is not Granted", Toast.LENGTH_LONG).show()
        }
    }

    private fun showPopup(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu_popup_choice_add_picture)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.add_by_camera -> {
                    takephoto.launch()
                }
                R.id.add_by_mediastore -> {
                    selectPictureInMedia()
                }
            }
            true
        })
        popup.show()
    }

    // ------ Toolbar ------
    private fun configureToolbar() {
        val addPropertyActivitytoolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(addPropertyActivitytoolbar)
        title = "Edit a Property"

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
             R.id.menu_add_property -> addHouseInRoomDatabaseAfterChange()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDataPropertySelectedToEdit() {
        val houseIdEdit = intent.extras?.getParcelable<House>("PropertyInFragment")

        // Property Id
        houseIdUpdate = houseIdEdit!!.houseId

        //Property type
        binding.addPropertyViewDropdownType.setText(houseIdEdit.detailViewType)

        // Price
        binding.addPropertyViewPrice.setText(houseIdEdit.detailViewPrice)

        // Surface
        binding.addPropertyViewSurface.setText(houseIdEdit.detailsViewSurface)

        // Room
        binding.addPropertyViewRoom.setText(houseIdEdit.detailsViewRooms)

        // Bath
        binding.addPropertyViewBathroom.setText(houseIdEdit.detailsViewBath)

        // Bed
        binding.addPropertyViewBedroom.setText(houseIdEdit.detailsViewBed)

        // Description
        binding.addPropertyViewDescription.setText(houseIdEdit.detailsViewDescription)

        // Address
        binding.addPropertyViewAddress.setText(houseIdEdit.detailsAddress)

        //Neighbourhood
        binding.addPropertyViewNeighbourhood.setText(houseIdEdit.detailViewNearTitle)

        // Near by
        if (houseIdEdit.amenitySubway) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbySubway.isChecked = true
        }
        if (houseIdEdit.amenitySchool) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbySchool.isChecked = true
        }
        if (houseIdEdit.amenityShop) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyShop.isChecked = true
        }
        if (houseIdEdit.amenityPark) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyPark.isChecked = true
        }
        if (houseIdEdit.amenityPlayground) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyPlayground.isChecked = true
        }
        if (houseIdEdit.amenityBuses) {
            binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyBuses.isChecked = true
        }

        // On market since
        binding.addPropertyViewSince.setText(houseIdEdit.detailMarketSince)

        // Sold switch
        if (houseIdEdit.detailSold) {
            binding.addPropertyViewSoldSwitch.isChecked = true
        }

        // Sold on
        binding.addPropertyViewSoldOn.setText(houseIdEdit.detailSoldOn)

        // Agent add property
        binding.addPropertyViewDropdownAgent.setText(houseIdEdit.detailManageBy)

        //List of description
        lisDescriptionPicture = houseIdEdit.descriptionPictures

    }

    // For Save the data after change
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
    private fun addHouseInRoomDatabaseAfterChange() {

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

            val house = House(houseIdUpdate,
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
                textAgentAddHouse,getTheListofDescriptionPictures())

            addHouseViewModel.update(house)

            returnToMainActivity()
            showToastForUpdateHouse()
    }

    private fun getTheListofDescriptionPictures(): List<DescriptionPictures> {
        val descriptions = descriptionPictureList
        return descriptions
    }

    private fun  returnToMainActivity() {
        val mainActivity = Intent(this, MainActivity::class.java)
        startActivity(mainActivity)
    }

    private fun showToastForUpdateHouse() {
        Toast.makeText(this, "Les modifications ont bien été enregistrée", Toast.LENGTH_LONG).show()
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

    private fun savePhotoToInternalStorage(filename: String, bmp: Bitmap) : Boolean {
        return try {
            context.openFileOutput("$filename.jpg", Context.MODE_PRIVATE).use { stream ->
                if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw  IOException("Couldn't save bitmap")
                }
            }
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    // For the list of pictures and description
    private fun setUpRecyclerviewPictures() {
        setupInternalStorageRecyclerView()
        loadPhotosFromInternalStorageIntoRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupInternalStorageRecyclerView() = binding.addPropertyViewPictureRv.apply {
        adapter = listPictureDescriptionEditAdapter
        layoutManager = LinearLayoutManager(this@EditPropertyActivity, LinearLayoutManager.VERTICAL, false)

        val position = if (descriptionPictureList.isEmpty()) {
            0
        } else {
            descriptionPictureList.size -1
        }
        descriptionPictureList.add(DescriptionPictures("",houseIdUpdate,picturesId))

        listPictureDescriptionEditAdapter.notifyItemInserted(position)
        listPictureDescriptionEditAdapter.notifyDataSetChanged()
    }

    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
        lifecycleScope.launch {
            val photos = loadPhotosFromInternalStorage()
            listPictureDescriptionEditAdapter.submitList(photos)
        }
    }

    private suspend fun loadPhotosFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files = filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") && it.name.startsWith(houseIdUpdate) }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bmp)
            } ?: listOf()
        }
    }
}