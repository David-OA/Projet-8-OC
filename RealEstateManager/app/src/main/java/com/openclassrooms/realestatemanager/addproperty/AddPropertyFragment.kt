package com.openclassrooms.realestatemanager.addproperty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.openclassrooms.realestatemanager.R

class AddPropertyFragment : Fragment() {

    companion object {
        fun newInstance(): AddPropertyFragment {
            return AddPropertyFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_property, container, false)
    }


}