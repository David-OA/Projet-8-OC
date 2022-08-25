package com.openclassrooms.realestatemanager.maisonlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.ItemClickSupport
import com.openclassrooms.realestatemanager.MaisonsViewModel
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentMaisonsListBinding
import com.openclassrooms.realestatemanager.maisoninfos.InfoDetailsFragment
import com.openclassrooms.realestatemanager.model.Maison

class MaisonsListFragment : Fragment() {

    private val maisonsViewModel: MaisonsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentMaisonsListBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMaisonsListBinding.bind(view)

        // Initialize the adapter and set it to the RecyclerView.
        val adapter = MaisonsListAdapter { maison -> adapterOnClick(maison) }

        val recyclerView: RecyclerView = binding.recyclerView
        recyclerView.adapter = adapter

        maisonsViewModel.currentMaison.observe(viewLifecycleOwner) {
            it?.let {
                adapter.submitList(maisonsViewModel.maisonsData)
            }
        }

        ItemClickSupport.addTo(recyclerView, R.layout.maisons_list_item).setOnItemClickListener(object : ItemClickSupport.OnItemClickListener {
            override fun onItemClicked(recyclerView: RecyclerView?, position: Int, v: View?) {

                //Toast.makeText(context,"test",Toast.LENGTH_LONG).show()
                val maisonId = maisonsViewModel.maisonsData.get(position)

                val bundle = Bundle()
                bundle.putSerializable("test", maisonId)

                val fragment = InfoDetailsFragment()
                fragment.arguments = bundle

                fragmentManager?.beginTransaction()?.replace(R.id.detail_view, fragment)?.commitAllowingStateLoss()

                //clickOnHouseOfTheList(maisonId)

            }
        })
    }

    fun clickOnHouseOfTheList(maison: Maison) {

        //Toast.makeText(context,maison.id,Toast.LENGTH_LONG).show()
    }

    private fun adapterOnClick(maison: Maison) {


    }
    
}