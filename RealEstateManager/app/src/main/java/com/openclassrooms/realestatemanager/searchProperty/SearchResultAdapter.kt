package com.openclassrooms.realestatemanager.searchProperty

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.PropertyListItemBinding
import com.openclassrooms.realestatemanager.model.House
import com.squareup.picasso.Picasso
import java.io.File

class SearchResultAdapter(
    private val listSearchProperties: List<House>
) : RecyclerView.Adapter<SearchResultAdapter.SearchResultViewHolder>(){

    private lateinit var context: Context

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //                                     Adapter parts
    /////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        context = parent.context
        return SearchResultViewHolder(PropertyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        holder.bind()
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //                              ViewHolder Parts
    /////////////////////////////////////////////////////////////////////////////////////////////////
    inner class SearchResultViewHolder(val binding: PropertyListItemBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SdCardPath")
        fun bind() {
            val resultSearchListProperties = listSearchProperties[adapterPosition]

            binding.housePrice.text = resultSearchListProperties.detailViewPrice

            binding.houseType.text = resultSearchListProperties.detailViewType

            binding.houseNeighborhood.text = resultSearchListProperties.detailViewNearTitle

            if (resultSearchListProperties.descriptionPictures.isNotEmpty()) {
                val propertyId = resultSearchListProperties.descriptionPictures[0].houseId
                val pictureId = resultSearchListProperties.descriptionPictures[0].picturesId

                Picasso.get()
                    .load(File("/data/data/com.openclassrooms.realestatemanager/files/","$propertyId.$pictureId.jpg"))
                    .into(binding.houseImage)
            }
        }
    }

    override fun getItemCount(): Int {
        return listSearchProperties.size
    }
}