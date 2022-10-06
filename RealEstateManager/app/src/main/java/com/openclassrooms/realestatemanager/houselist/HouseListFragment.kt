package com.openclassrooms.realestatemanager.houselist

import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.addproperty.AddHouseViewModel
import com.openclassrooms.realestatemanager.addproperty.InternalStoragePhoto
import com.openclassrooms.realestatemanager.databinding.FragmentMaisonsListBinding
import com.openclassrooms.realestatemanager.houseinfos.InfoDetailsFragment
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HouseListFragment : Fragment() {

    private val addHouseViewModel: AddHouseViewModel by activityViewModels{
        ViewModelFactory(Injection.providesHouseRepository(requireContext()), Injection.providesAgentRepository(requireContext()))
    }


    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private var _binding: FragmentMaisonsListBinding? = null

    private val binding get() = _binding!!

    private var adapter: HouseListAdapter? = null

    var tabletSize: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMaisonsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // For permission Internal storage
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            isReadPermissionGranted = permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] ?: isReadPermissionGranted
            isWritePermissionGranted = permissions[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: isWritePermissionGranted

        }

        requestPermission()

        recyclerViewListHouse()

        clickOnHouseOfTheList()

    }

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

    private fun recyclerViewListHouse() {
        // Initialize the adapter and set it to the RecyclerView.
        val adapter = HouseListAdapter {}

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter

        addHouseViewModel.allHouses.observe(viewLifecycleOwner) {
            houses -> houses?.let { adapter.submitList(it) }
        }
    }

    /* For pictures in the recyclerview
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
    }*/



    private fun clickOnHouseOfTheList() {
        addHouseViewModel.allHouses.observe(viewLifecycleOwner) {
        ItemClickSupport.addTo(binding.recyclerView, R.layout.property_list_item).setOnItemClickListener { recyclerView, position, v ->

                // Get id property click in recyclerview
                val maisonId = addHouseViewModel.allHouses.value?.get(position)

                val bundle = Bundle()
                if (maisonId != null) {
                    bundle.putSerializable("houseClicked", maisonId)
                }

                val fragment = InfoDetailsFragment()
                fragment.arguments = bundle

                // For show if tablet or phone
                tabletSize = resources.getBoolean(R.bool.isTablet)
                if (tabletSize) {
                    fragmentManager?.beginTransaction()?.replace(R.id.detail_view, fragment)
                        ?.commitAllowingStateLoss()

                } else {
                    fragmentManager?.beginTransaction()?.replace(R.id.recycler_view_list_house, fragment)
                        ?.commitAllowingStateLoss()
                }
            }
        }
    }
}