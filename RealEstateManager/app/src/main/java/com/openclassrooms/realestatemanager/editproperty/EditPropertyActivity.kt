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
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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
import com.openclassrooms.realestatemanager.model.InternalStoragePhoto
import com.openclassrooms.realestatemanager.addproperty.ListAgentSpinnerAdapter
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.model.DescriptionPictures
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import com.openclassrooms.realestatemanager.utils.TypeProperty
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*

class EditPropertyActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddPropertyBinding

    private var playgroundCheck: Boolean = false
    private var schoolCheck: Boolean = false
    private var shopCheck: Boolean = false
    private var subwayCheck: Boolean = false
    private var parkCheck: Boolean = false
    private var busesCheck: Boolean = false

    private var switchSoldCheck: Boolean = false

    private lateinit var houseIdUpdate: String

    private var compareValue: String = ""

    private var houseLat: Double = 0.0
    private var houseLng: Double = 0.0

    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var context: Context

    // RecyclerView
    private lateinit var listPictureDescriptionEditAdapter: ListPictureDescriptionEditAdapter
    private val photoList : MutableList<InternalStoragePhoto> = mutableListOf()
    private var descriptionPictureList: MutableList<DescriptionPictures> = mutableListOf()

    private val addHouseViewModel: AddHouseViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    private val addAgentViewModel: AddAgentViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    private val dropdownTypeHouse by lazy { binding.addPropertyViewDropdownType }

    private val dropdownAddAgent by lazy { binding.addPropertyViewDropdownAgent }

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

        requestPermission()
        
        // For get data and show
        getDataPropertySelectedToEdit()

        // For edit change
        getHouseType()
        getAgentInTheList()
        getClickOnTheListAgent()

        setUpRecyclerviewPictures()
        clickOnTextViewForAddOrChangeDescription()

        loadPhotosFromInternalStorageIntoRecyclerView()

        choiceHowTakeAPicture()

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Add a picture
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun choiceHowTakeAPicture() {
        binding.addPropertyViewAddPictureButton.setOnClickListener {
            showPopup(binding.addPropertyViewAddPictureButton)
        }
    }

    // For start camera and take a photo
    private val takephoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        lifecycleScope.launch {
            if (isWritePermissionGranted) {
                val picturesId: String = UUID.randomUUID().toString()
                if (savePhotoToInternalStorage("$houseIdUpdate.$picturesId", it!!)) {
                    Toast.makeText(this@EditPropertyActivity, "Photo Saved Successfully", Toast.LENGTH_LONG).show()

                    photoList.add(InternalStoragePhoto("$houseIdUpdate.$picturesId",it,""))

                    setUpRecyclerviewPictures()

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
                val picturesId: String = UUID.randomUUID().toString()
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver,imageUrl)
                if (savePhotoToInternalStorage("$houseIdUpdate.$picturesId", bitmap)) {
                    Toast.makeText(this@EditPropertyActivity, "Photo Saved Successfully", Toast.LENGTH_LONG).show()

                    photoList.add(InternalStoragePhoto("$houseIdUpdate.$picturesId",bitmap,""))

                    setUpRecyclerviewPictures()

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

    // For choice menu
    private fun showPopup(view: View) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu_popup_choice_add_picture)

        popup.setOnMenuItemClickListener { item: MenuItem? ->
            when (item!!.itemId) {
                R.id.add_by_camera -> {
                    takephoto.launch()
                }
                R.id.add_by_mediastore -> {
                    selectPictureInMedia()
                }
            }
            true
        }
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Data
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
        compareValue = houseIdEdit.detailManageBy

        //List of description
        descriptionPictureList = houseIdEdit.descriptionPictures as MutableList<DescriptionPictures>

        // For lat and lng
        houseLat = houseIdEdit.detailLatitude
        houseLng = houseIdEdit.detailLongitude

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

    // For get Agent
    private fun  getAgentInTheList() {
        addAgentViewModel.getAllAgent.observe(this){
            val agent: List<Agent> = addAgentViewModel.getAllAgent.value!!

            val customDropDownAdapter = ListAgentSpinnerAdapter(this,agent)

            dropdownAddAgent.adapter = customDropDownAdapter

            val spinnerPosition: Int =  customDropDownAdapter.getPosition(compareValue)

            dropdownAddAgent.setSelection(spinnerPosition)
        }
    }

    private fun getClickOnTheListAgent() {
        dropdownAddAgent.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val agent = dropdownAddAgent.selectedItem as Agent
                addAgentViewModel.getNameClicked(agent)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
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
            val schoolCheckBox = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbySchool
            val playgroundCheckBox = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyPlayground
            val shopCheckBox = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyShop
            val busesCheckBox = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyBuses
            val subwayCheckBox = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbySubway
            val parkCheckBox = binding.addPropertyViewNearbyGridlayoutNearby.checkboxNearbyPark

            if (schoolCheckBox.isChecked) {
                schoolCheck = true
            }
            if (playgroundCheckBox.isChecked) {
                playgroundCheck = true
            }
            if (shopCheckBox.isChecked) {
                shopCheck = true
            }
            if (busesCheckBox.isChecked) {
                busesCheck = true
            }
            if (subwayCheckBox.isChecked) {
                subwayCheck = true
            }
            if (parkCheckBox.isChecked) {
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
                playgroundCheck,
                shopCheck,
                subwayCheck,
                parkCheck,
                textDescriptionTextView,
                textAdressTextView,
                textNeighbourhoodTextView,
                textOnMarketSince,
                switchSoldCheck,
                textSoldOn,
                textAgentAddHouse,houseLat,houseLng,getTheListOfDescriptionsPictures())

            addHouseViewModel.update(house)

            returnToMainActivity()
            showToastForUpdateHouse()
    }

    private fun getTheListOfDescriptionsPictures(): List<DescriptionPictures> {
        return descriptionPictureList
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Save and load picture in internal storage
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // For save picture in internal storage
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

    // For load pictures
    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
        lifecycleScope.launch {
            // For recyclerview
            val photos = loadPhotosFromInternalStorage()

            var i = 0
            for (descriptionPicture in descriptionPictureList) {
                photos[i].description = descriptionPicture.description
                i++
            }

            photoList.addAll(photos)
            
            setupInternalStorageRecyclerView()
        }
    }

    // For load pictures
    private suspend fun loadPhotosFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(IO) {
            val files = context.filesDir?.listFiles()

            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") && it.name.startsWith(houseIdUpdate) }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                InternalStoragePhoto(it.name,bmp,"")
            } ?: listOf()
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Recyclerview
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // For the list of pictures and description
    private fun setUpRecyclerviewPictures() {
        setupInternalStorageRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupInternalStorageRecyclerView() = binding.addPropertyViewPictureRv.apply {
        listPictureDescriptionEditAdapter = ListPictureDescriptionEditAdapter(photoList)
        adapter = listPictureDescriptionEditAdapter
        layoutManager = LinearLayoutManager(this@EditPropertyActivity, LinearLayoutManager.VERTICAL, false)
        listPictureDescriptionEditAdapter.notifyDataSetChanged()
    }


    // For manage clicks
    private fun clickOnTextViewForAddOrChangeDescription() {
        ItemClickSupport.addTo(binding.addPropertyViewPictureRv, R.layout.list_pictures_added_item).setOnItemClickListener { recyclerView, position, v ->
            v.findViewById<TextView>(R.id.pictures_added_textview).setOnClickListener {
                showDialogForAddDescription(position)
            }
            v.findViewById<ImageButton>(R.id.pictures_added_rv_delete_button).setOnClickListener {
                showDialogForDeletePicture(position)
            }
        }
    }

    // For add descriptions
    @SuppressLint("NotifyDataSetChanged")
    fun addPicturesDescription(position: Int, description:String, houseId: String, picturesId: String) {
        if(descriptionPictureList.getOrNull(position) == null) {
            descriptionPictureList.add(DescriptionPictures(description, houseId, picturesId,descriptionPictureList.size -1))
        } else {
            descriptionPictureList[position] = DescriptionPictures(description, houseId, picturesId, position)
        }
    }

    // For add or change description
    private fun showDialogForAddDescription(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Description")

        val input = EditText(this)
        input.hint = "Enter Description"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            val descriptionAlertDialog = input.text.toString()
            val elementClick = photoList.getOrNull(position)
            if (elementClick != null) {
                elementClick.description = descriptionAlertDialog
            }
            setUpRecyclerviewPictures()
            val idClick = elementClick?.name?.substringAfter(".", ".jpg")
            val id = removeLastJpg(idClick)
            val propertyIdClick = elementClick?.name?.subSequence(0, 36).toString()
            if (idClick != null) {
                if (id != null) {
                    addPicturesDescription(position, descriptionAlertDialog, propertyIdClick,id)
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun removeLastJpg(str: String?): String? {
        return str?.replaceFirst(".jpg".toRegex(),"")
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Delete picture
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // For delete picture
    private fun showDialogForDeletePicture(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Delete")

        builder.setPositiveButton("OK") { _, _ ->

            val elementClick = photoList.getOrNull(position)
            photoList.removeAt(position)
            if (elementClick != null) {
                deletePhotoInInternalStorage(elementClick.name)
            }
            setupInternalStorageRecyclerView()
            descriptionPictureList.removeAt(position)
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    // For delete picture in internal storage
    private fun deletePhotoInInternalStorage(filename: String) : Boolean {
        return try {
            deleteFile(filename)
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}