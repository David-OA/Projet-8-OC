package com.openclassrooms.realestatemanager.houselist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.utils.ItemClickSupport
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.addproperty.AddHouseViewModel
import com.openclassrooms.realestatemanager.databinding.FragmentMaisonsListBinding
import com.openclassrooms.realestatemanager.houseinfos.InfoDetailsFragment
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.House

class HouseListFragment : Fragment() {

    private val addHouseViewModel: AddHouseViewModel by activityViewModels{
        ViewModelFactory(Injection.providesHouseRepository(requireContext()), Injection.providesAgentRepository(requireContext()))
    }

    private var _binding: FragmentMaisonsListBinding? = null

    private val binding get() = _binding!!

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

        recyclerViewListHouse()

        clickOnHouseOfTheList()

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


    private fun clickOnHouseOfTheList() {
        addHouseViewModel.allHouses.observe(viewLifecycleOwner) {
        ItemClickSupport.addTo(binding.recyclerView, R.layout.maisons_list_item).setOnItemClickListener { recyclerView, position, v -> setPropertySelected(addHouseViewModel.allHouses.value?.get(position))

                // Get id property click in recyclerview
                val maisonId = addHouseViewModel.allHouses.value?.get(position)


                val bundle = Bundle()
                if (maisonId != null) {
                    bundle.putSerializable("test", maisonId)
                }

                val fragment = InfoDetailsFragment()
                fragment.arguments = bundle

                fragmentManager?.beginTransaction()?.replace(R.id.detail_view, fragment)
                    ?.commitAllowingStateLoss()
            }
        }
    }

    private fun setPropertySelected(house: House?) {
        if (house != null) {
            addHouseViewModel.getHouseSelected(house)
        }
    }
    
}