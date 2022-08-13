package com.openclassrooms.realestatemanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.databinding.SearchPropertyViewBinding

class SearchPropertyActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = SearchPropertyViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}
