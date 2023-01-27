package com.openclassrooms.realestatemanager.propertyinfos

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentPropertyInfosBinding
import com.openclassrooms.realestatemanager.editproperty.EditPropertyActivity
import com.openclassrooms.realestatemanager.model.DescriptionPictures
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.utils.Utils

class InfoDetailsFragment : Fragment(), OnMapReadyCallback {

    private var map: GoogleMap? = null

    private lateinit var internalStorageInfoDetailsAdapter: InfoDetailsAdapter

    private var houseIdEdit: House? = null

    private var isReadPermissionGranted = false
    private var isWritePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private var _binding: FragmentPropertyInfosBinding? = null
    private val binding get() = _binding!!

    // For RecyclerView
    private var listDescriptionPicture: List<DescriptionPictures> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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

        // Get data from click on item recyclerview list properties
        val args = this.arguments
        if (args != null) {
            val houseId = args.getParcelable<House>("houseClicked") as House
            houseIdEdit = houseId

            binding.detailsViewDescription.text = houseId.detailsViewDescription
            binding.detailsViewSurface.text = houseId.detailsViewSurface
            binding.detailsViewRooms.text = houseId.detailsViewRooms
            binding.detailsViewBath.text = houseId.detailsViewBath
            binding.detailsViewBed.text = houseId.detailsViewBed

            val sharedPref = context?.getSharedPreferences("CHANGE_CURRENCY", Context.MODE_PRIVATE)
            val currencyValue = sharedPref?.getString("CHANGE_CURRENCY","")
            if (currencyValue == "currencyDollars"){
                binding.detailViewPrice.text = Utils.numberFormat(Utils.convertEuroToDollar(houseId.detailViewPrice.toInt()))
            } else if (currencyValue == "currencyEuros") {
                binding.detailViewPrice.text = Utils.numberFormat(houseId.detailViewPrice.toInt())
                binding.detailViewPriceIcon.setImageResource(R.drawable.euro_icon)
            }

            binding.detailViewType.text = houseId.detailViewType

            if (houseId.amenityBuses) {
                binding.detailViewAmenityOne.setImageResource(R.drawable.bus_icon)
            }
            if (houseId.amenityPark) {
                binding.detailViewAmenityTwo.setImageResource(R.drawable.park_icon)
            }
            if (houseId.amenityPlayground) {
                binding.detailViewAmenityThree.setImageResource(R.drawable.playground_icon)
            }
            if (houseId.amenityShop) {
                binding.detailViewAmenityFour.setImageResource(R.drawable.shopping_icon)
            }
            if (houseId.amenitySchool) {
                binding.detailViewAmenityFive.setImageResource(R.drawable.school_icon)
            }
            if (houseId.amenitySubway) {
                binding.detailViewAmenitySix.setImageResource(R.drawable.subway_icon)
            }

            listDescriptionPicture = houseId.descriptionPictures

            binding.detailsViewLocationCity.text = houseId.detailViewNearTitle
            binding.detailsViewLocationStreet.text = houseId.detailsAddress

            setUpMarker()

        }

        configureMap()

        // For the recyclerview
        if (houseIdEdit != null) {
            setupInternalStoragePicturesRecyclerView()
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For Toolbar
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Menu Toolbar
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_detail_view_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Deprecated("Deprecated in Java")
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For map view
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // For Map View
    private fun configureMap() {
        // Get the map and register for the ready callback
        val mapFragment = childFragmentManager.findFragmentById(R.id.details_view_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setUpMarker()
    }

    // For map view when click
    private fun addMarkers() {
        map?.addMarker(
            MarkerOptions().position(LatLng(houseIdEdit!!.detailLatitude,houseIdEdit!!.detailLongitude))
        )
    }

    private fun moveCamera() {
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(houseIdEdit!!.detailLatitude,houseIdEdit!!.detailLongitude),13f))
    }

    private fun setUpMarker() {
        if (map == null ) {
          return
        }
        if (houseIdEdit != null){
            addMarkers()
            moveCamera()
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // For recyclerview
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // For recyclerview
    @SuppressLint("NotifyDataSetChanged")
    private fun setupInternalStoragePicturesRecyclerView() = binding.detailViewCardPictures.apply {
        internalStorageInfoDetailsAdapter = InfoDetailsAdapter(listDescriptionPicture)
        adapter = internalStorageInfoDetailsAdapter
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        internalStorageInfoDetailsAdapter.notifyDataSetChanged()
    }

}