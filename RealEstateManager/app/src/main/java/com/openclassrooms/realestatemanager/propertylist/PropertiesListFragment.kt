package com.openclassrooms.realestatemanager.propertylist

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.addproperty.AddHouseViewModel
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyListBinding
import com.openclassrooms.realestatemanager.propertyinfos.InfoDetailsFragment
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory

class PropertiesListFragment : Fragment() {

    // ViewModel
    private val addHouseViewModel: AddHouseViewModel by activityViewModels{
        ViewModelFactory(Injection.providesHouseRepository(requireContext()), Injection.providesAgentRepository(requireContext()))
    }

    // For permissions
    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private var _binding: FragmentPropertyListBinding? = null
    private val binding get() = _binding!!

    // For screen if tablet or phone
    var tabletSize: Boolean = false

    // Recyclerview
    private var adapter: PropertiesListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPropertyListBinding.inflate(inflater, container, false)
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
        adapter = PropertiesListAdapter ()

        // Getting the recyclerview by its id binding
        val recyclerView: RecyclerView = binding.recyclerView

        // Setting the Adapter with the recyclerview
        recyclerView.adapter = adapter

        // Pass the data to our Adapter
        addHouseViewModel.allHouses.observe(viewLifecycleOwner) {
            houses -> houses?.let { adapter?.submitList(it) }
        }

        clickOnHouseOfTheList()
    }

    private fun clickOnHouseOfTheList() {
        ItemClickSupport.addTo(binding.recyclerView, R.layout.property_list_item).setOnItemClickListener { recyclerView, position, v ->

            adapter!!.updateSelection(position)

            // Get id property click in recyclerview
            val propertyClick = addHouseViewModel.allHouses.value?.get(position)

            // Get Data House clicked for pass to the fragment
            val bundle = Bundle()
            if (propertyClick != null) {
                bundle.putParcelable("houseClicked", propertyClick)
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