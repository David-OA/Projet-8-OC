package com.openclassrooms.realestatemanager.addproperty


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.openclassrooms.realestatemanager.data.AddHouseViewModel
import com.openclassrooms.realestatemanager.data.HouseDao
import com.openclassrooms.realestatemanager.data.repository.HouseRepository
import com.openclassrooms.realestatemanager.databinding.FragmentAddPropertyBinding
import com.openclassrooms.realestatemanager.injection.Injection
import com.openclassrooms.realestatemanager.injection.ViewModelFactory
import com.openclassrooms.realestatemanager.model.House


class AddPropertyFragment: Fragment() {

    private var _binding: FragmentAddPropertyBinding? = null

    private val binding get() = _binding!!


    private val addHouseViewModel: AddHouseViewModel by activityViewModels{
        ViewModelFactory(Injection.providesHouseRepository(requireContext()))
    }

    private val newHouseActivityRequestCode = 1

    private lateinit var editDescriptionTextView: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPropertyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val btn = binding.
        btn.setOnClickListener {
            val str: String = binding.addPropertyViewAddress.text.toString()
            Toast.makeText(context,"The value is $str",Toast.LENGTH_LONG).show()
        }*/

        //
        //viewModel for add house in database
        val fab = binding.fab
        fab.setOnClickListener{
            val house = House(1,
                "Jolie palace ",
                "850",
                "20",
                "15",
                "15",
                "18 250 000",
                "Palace",
                "Nice")

            addHouseViewModel.insert(house)
        }


    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newHouseActivityRequestCode) {


            //data?.getBundleExtra(AddPropertyFragment())?.let {

            //}
        } else {
            Toast.makeText(context,"ca ne marche pas", Toast.LENGTH_LONG).show()
        }
    }*/




}