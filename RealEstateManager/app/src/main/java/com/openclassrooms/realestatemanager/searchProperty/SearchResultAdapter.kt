package com.openclassrooms.realestatemanager.searchProperty

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.PropertyListItemBinding
import com.openclassrooms.realestatemanager.model.House

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
        fun bind() {

            val resultSearchListProperties = listSearchProperties[adapterPosition]

            binding.housePrice.text = resultSearchListProperties.detailViewPrice

        }
    }


    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<House>() {
            override fun areItemsTheSame(oldItem: House, newItem: House): Boolean {
                return (
                        oldItem.houseId == newItem.houseId ||
                                oldItem.detailsViewDescription == newItem.detailsViewDescription ||
                                oldItem.detailsViewSurface == newItem.detailsViewSurface
                        )
            }

            override fun areContentsTheSame(oldItem: House, newItem: House): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return listSearchProperties.size
    }
}