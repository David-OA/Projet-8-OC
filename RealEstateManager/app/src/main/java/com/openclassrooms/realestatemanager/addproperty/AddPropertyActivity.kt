package com.openclassrooms.realestatemanager.addproperty


import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
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
import com.openclassrooms.realestatemanager.databinding.ActivityAddPropertyBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.DescriptionPictures
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import com.openclassrooms.realestatemanager.utils.TypeProperty
import com.openclassrooms.realestatemanager.utils.idGeneratedProperty
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*


class AddPropertyActivity: AppCompatActivity() {

    private lateinit var binding: ActivityAddPropertyBinding

    // For checkBoxes
    private var playgroundCheck: Boolean = false
    private var schoolCheck: Boolean = false
    private var shopCheck: Boolean = false
    private var subwayCheck: Boolean = false
    private var parkCheck: Boolean = false
    private var busesCheck: Boolean = false

    private var switchSoldCheck: Boolean = false

    private var textPriceTextView: String = ""
    private var textSurfaceTextView: String = ""
    private var textRoomTextView: String = ""
    private var textBathTextView: String = ""
    private var textBedTextView: String = ""
    private var textDescriptionTextView: String = ""
    private var textAddressTextView: String = ""
    private var textNeighbourhoodTextView: String = ""
    private var textOnMarketSince: String = ""

    // For auto generated id
    private var propertyId: String = idGeneratedProperty

    // For permissions
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private lateinit var context: Context

    // ViewModels
    private val addHouseViewModel: AddHouseViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    private val addAgentViewModel: AddAgentViewModel by viewModels {
        ViewModelFactory(Injection.providesHouseRepository(this), Injection.providesAgentRepository(this))
    }

    private val dropdownTypeHouse by lazy { binding.addPropertyViewDropdownType }

    // RecyclerView
    private lateinit var listPictureDescriptionAdapter: ListPictureDescriptionAdapter
    private val photoList : MutableList<InternalStoragePhoto> = mutableListOf()
    private val descriptionPictureList: MutableList<DescriptionPictures> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityAddPropertyBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        this.context = this@AddPropertyActivity

        setUpRecyclerviewPictures()

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
            isWritePermissionGranted = permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted

        }

        requestPermission()

        getHouseType()
        configureToolbar()
        choiceHowTakeAPicture()
        onClickForAddAgent()

        clickOnTextViewForAddOrChangeDescription()
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
                if (savePhotoToInternalStorage("$propertyId.$picturesId", it!!)) {
                    Toast.makeText(this@AddPropertyActivity, "Photo Saved Successfully", Toast.LENGTH_LONG).show()

                    photoList.add(InternalStoragePhoto("$propertyId.$picturesId",it,""))

                    setUpRecyclerviewPictures()
                } else {
                    Toast.makeText(this@AddPropertyActivity, "Failed to Save photo", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@AddPropertyActivity,"Permission not granted", Toast.LENGTH_LONG).show()
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
                if (savePhotoToInternalStorage("$propertyId.$picturesId", bitmap)) {
                    Toast.makeText(this@AddPropertyActivity, "Photo Saved Successfully", Toast.LENGTH_LONG).show()

                    photoList.add(InternalStoragePhoto("$propertyId.$picturesId",bitmap,""))

                    setUpRecyclerviewPictures()
                } else {
                    Toast.makeText(this@AddPropertyActivity, "Failed to Save photo", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this@AddPropertyActivity,"Permission not granted", Toast.LENGTH_LONG).show()
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

    // For choice where take a picture
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Toolbar
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // ------ Toolbar ------
    private fun configureToolbar() {
        val addPropertyActivitytoolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(addPropertyActivitytoolbar)
        title = "Add a Property"
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
           R.id.menu_add_property -> checkNoErrorBeforeAddHouse()
        }
        return super.onOptionsItemSelected(item)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For add a property
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun checkNoErrorBeforeAddHouse() {
        if (binding.addPropertyViewPrice.text.toString().isEmpty() || binding.addPropertyViewSurface.text.toString().isEmpty()
            || binding.addPropertyViewRoom.text.toString().isEmpty() || binding.addPropertyViewBathroom.text.toString().isEmpty()
            || binding.addPropertyViewBedroom.text.toString().isEmpty() || binding.addPropertyViewDescription.text.toString().isEmpty()
            || binding.addPropertyViewAddress.text.toString().isEmpty() || binding.addPropertyViewNeighbourhood.text.toString().isEmpty()
            || binding.addPropertyViewSince.text.toString().isEmpty()) {
            Toast.makeText(this,"You forgot to fill in a field",Toast.LENGTH_LONG).show()
        } else {
            addHouseInRoomDatabase()
        }

    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun addHouseInRoomDatabase() {
        //Price
        val editPriceTextView = binding.addPropertyViewPrice
        textPriceTextView = editPriceTextView.text.toString()

        //Surface
        val editSurfaceTextView = binding.addPropertyViewSurface
        textSurfaceTextView = editSurfaceTextView.text.toString()

        //Room
        val editRoomTextView = binding.addPropertyViewRoom
        textRoomTextView = editRoomTextView.text.toString()

        //Bath
        val editBathTextView = binding.addPropertyViewBathroom
        textBathTextView = editBathTextView.text.toString()

        //Bed
        val editBedTextView = binding.addPropertyViewBedroom
        textBedTextView = editBedTextView.text.toString()

        //Description
        val editDescriptionTextView = binding.addPropertyViewDescription
        textDescriptionTextView = editDescriptionTextView.text.toString()

        //Address
        val editAdressTextView = binding.addPropertyViewAddress
        textAddressTextView = editAdressTextView.text.toString()

        //Neighbourhood
        val editNeighbourhoodTextView = binding.addPropertyViewNeighbourhood
        textNeighbourhoodTextView = editNeighbourhoodTextView.text.toString()

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

        // type Property
        val  typeHouseChoice = dropdownTypeHouse.text.toString()

        //On market since
        val onMarketSince = binding.addPropertyViewSince
        textOnMarketSince = onMarketSince.text.toString()

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
            playgroundCheck,
            shopCheck,
            subwayCheck,
            parkCheck,
            textDescriptionTextView,
            textAddressTextView,
            textNeighbourhoodTextView,
            textOnMarketSince,
            switchSoldCheck,
            textSoldOn,
            textAgentAddHouse,getTheListOfDescriptionPictures())

        addHouseViewModel.insert(house)

        returnToMainActivity()
        showToastForAddHouse()
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


    private fun onClickForAddAgent() {
        binding.addPropertyViewDropdownAgent.setOnClickListener {
            getAgentInTheList()
        }
    }

    private fun getAgentInTheList() {
        addAgentViewModel.getAllAgent.observe(this) {
            val listAgent = addAgentViewModel.getAllAgent.value
            val dialog = ListAgentsDialogView(listAgent!!)
            dialog.show(supportFragmentManager.beginTransaction(), "tag test")

        }
        showAgentListSelected()
    }

    private fun showAgentListSelected() {
        val agentSelected = addAgentViewModel.getAgentClick.value.toString()
        binding.addPropertyViewDropdownAgent.setText(agentSelected)
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For manage some actions
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For save pictures pictures
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private fun savePhotoToInternalStorage(filename: String, bmp: Bitmap) : Boolean {
        return try {
            context.openFileOutput("$filename.jpg",Context.MODE_PRIVATE).use { stream ->
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For manage the recyclerview
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // For the list of pictures and description
    private fun setUpRecyclerviewPictures() {
        setupInternalStorageRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupInternalStorageRecyclerView() = binding.addPropertyViewPictureRv.apply {
        listPictureDescriptionAdapter = ListPictureDescriptionAdapter(photoList)
        adapter = listPictureDescriptionAdapter
        layoutManager = LinearLayoutManager(this@AddPropertyActivity, LinearLayoutManager.VERTICAL, false)
        listPictureDescriptionAdapter.notifyDataSetChanged()
    }

    private fun getTheListOfDescriptionPictures(): List<DescriptionPictures> {
        return descriptionPictureList
    }

    private fun clickOnTextViewForAddOrChangeDescription() {
        ItemClickSupport.addTo(binding.addPropertyViewPictureRv, R.layout.list_pictures_added_item).setOnItemClickListener { _, position, _ ->
            showDialogForAddDescription(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addPicturesDescription(position: Int, description:String, houseId: String, picturesId: String) {
        if (descriptionPictureList.getOrNull(position) == null) {
            descriptionPictureList.add(DescriptionPictures(description,houseId, picturesId))

        } else {
            descriptionPictureList[position] = DescriptionPictures(description,houseId, picturesId)
        }
    }

    private fun showDialogForAddDescription(position: Int) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Description")

        val input = EditText(this)
        input.hint = "Enter Description"
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            val descriptionAlertDialog = input.text.toString()
            val elementClick = photoList.getOrNull(position)
            if (elementClick != null) {
                photoList[position] = elementClick.copy(description = descriptionAlertDialog)
            }

            setUpRecyclerviewPictures()

            val id = elementClick?.name?.substringAfter(".",".jpg")
            val propertyIdClick = elementClick?.name?.subSequence(0,36).toString()
            if (id != null) {
                addPicturesDescription(position,descriptionAlertDialog, propertyIdClick,id)
            }
        })

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}