package com.openclassrooms.realestatemanager.propertyinfos

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.addproperty.AddHouseViewModel
import com.openclassrooms.realestatemanager.addproperty.InternalStoragePhoto
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyInfosBinding
import com.openclassrooms.realestatemanager.editproperty.EditPropertyActivity
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.DescriptionPictures
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InfoDetailsFragment : Fragment() {

    private val addHouseViewModel: AddHouseViewModel by activityViewModels{
        ViewModelFactory(Injection.providesHouseRepository(requireContext()), Injection.providesAgentRepository(requireContext()))
    }

    private lateinit var internalStoragePictureAdapter: PictureAdapter

    private var houseIdEdit: House? = null

    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>


    private var _binding: FragmentPropertyInfosBinding? = null
    private val binding get() = _binding!!

    private var listDescriptionPicture: List<DescriptionPictures> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPropertyInfosBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentPropertyInfosBinding.bind(view)

        setHasOptionsMenu(true)

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
            isWritePermissionGranted = permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted

        }

        //requestPermission()

        val args = this.arguments
        if (args != null) {
            val houseId = args.getParcelable<House>("houseClicked") as House
            houseIdEdit = houseId

            binding.detailsViewDescription.text = houseId.detailsViewDescription
            binding.detailsViewSurface.text = houseId.detailsViewSurface
            binding.detailsViewRooms.text = houseId.detailsViewRooms
            binding.detailsViewBath.text = houseId.detailsViewBath
            binding.detailsViewBed.text = houseId.detailsViewBed
            binding.detailViewPrice.text = houseId.detailViewPrice
            binding.detailViewType.text = houseId.detailViewType
            if (houseId.amenityBuses == true) {
                binding.detailViewAmenityOne.setImageResource(R.drawable.bus_icon)
            }
            if (houseId.amenityPark == true) {
                binding.detailViewAmenityTwo.setImageResource(R.drawable.park_icon)
            }
            if (houseId.amenityPlayground == true) {
                binding.detailViewAmenityThree.setImageResource(R.drawable.playground_icon)
            }
            if (houseId.amenityShop == true) {
                binding.detailViewAmenityFour.setImageResource(R.drawable.shopping_icon)
            }
            if (houseId.amenitySchool == true) {
                binding.detailViewAmenityFive.setImageResource(R.drawable.school_icon)
            }
            if (houseId.amenitySubway == true) {
                binding.detailViewAmenitySix.setImageResource(R.drawable.subway_icon)
            }

            listDescriptionPicture = houseId.descriptionPictures

        }

        this.internalStoragePictureAdapter = PictureAdapter(listDescriptionPicture.toMutableList())

        if (houseIdEdit != null) {
            loadPhotosFromInternalStorageIntoRecyclerView()
            setupInternalStoragePicturesRecyclerView()
        }
    }

    private fun getTheListOfDescriptionsPictures(): List<DescriptionPictures> {
        val descriptionPictures = listDescriptionPicture
        return descriptionPictures
    }


    // Menu Toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater!!.inflate(R.menu.menu_toolbar_detail_view_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.edit -> openEditPropertyActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    // For Edit option
    private fun openEditPropertyActivity() {
        val clickForEditPropertyActivity = Intent(requireContext(), EditPropertyActivity::class.java)
        clickForEditPropertyActivity.putExtra("PropertyInFragment", houseIdEdit as Parcelable)
        startActivity(clickForEditPropertyActivity)
    }

    // For pictures

    // For managed request permissions
    private fun requestPermission() {
        val isReadPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val isWritePermission = ContextCompat.checkSelfPermission(
            requireContext(),
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

    private fun setupInternalStoragePicturesRecyclerView() = binding.detailViewCardPictures.apply {
        adapter = internalStoragePictureAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun loadPhotosFromInternalStorageIntoRecyclerView() {
        lifecycleScope.launch {
            // For recyclerview
            val photos = loadPhotosFromInternalStorage()
            internalStoragePictureAdapter.submitList(photos)
        }
    }

    private suspend fun loadPhotosFromInternalStorage(): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files = requireContext().filesDir?.listFiles()

            files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") && it.name.startsWith(houseIdEdit!!.houseId) }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name,bmp)
            } ?: listOf()
        }
    }

}