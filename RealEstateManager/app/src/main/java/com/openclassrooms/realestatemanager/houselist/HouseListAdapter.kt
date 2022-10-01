/*
 * Copyright (c) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.openclassrooms.realestatemanager.houselist

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.PropertyListItemBinding
import com.openclassrooms.realestatemanager.model.House

class HouseListAdapter(private val onItemClicked: (House) -> Unit) :
    ListAdapter<House, HouseListAdapter.PropertyViewHolder>(DiffCallback) {


    private lateinit var context: Context
    var itemSelected: Int? = null

    private val viewHolders = mutableListOf<PropertyViewHolder>()


    // ViewHolder Parts
    class PropertyViewHolder(private var binding: PropertyListItemBinding, val  onItemClicked: (House) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var background: CardView

        private var backgroundColor: Int? = null

        private var isSelected = false
        private lateinit var context: Context

        init {
            backgroundColor = binding.listPropertyItemBackground.cardBackgroundColor.defaultColor
        }

        fun bind(house: House, context: Context, isSelected: Boolean) {

            this.isSelected = isSelected
            this.context = context

            binding.houseType.text = house.detailViewType
            binding.houseNeighborhood.text = house.detailViewNearTitle
            binding.housePrice.text = house.detailViewPrice
            //binding.houseImage.load(house.detailsViewListPictures)


            if (isSelected) configureCardToSelectedState()


        }

        fun updateSelection(positionSelected: Int) {
            if (positionSelected != null) {
                if (this.adapterPosition == positionSelected) {
                    isSelected = true
                } else {
                    isSelected = false
                }
            } else {

            }
        }

        private fun configureCardToSelectedState(){
            background.setCardBackgroundColor(getColor(context, R.color.colorAccent))
            //price.setTextColor(getColor(context, R.color.colorTextAccent))
        }
    }



    // Adapter parts
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.property_list_item, parent, false)
        val holder = PropertyViewHolder(PropertyListItemBinding.bind(view), onItemClicked)
        viewHolders.add(holder)
        //return PropertyViewHolder(PropertyListItemBinding.bind(view), onItemClicked)
        return holder
    }

    var isSelected: Boolean = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val current = getItem(position)


        holder.itemView.setOnClickListener {
            onItemClicked(current)
            isSelected = position == itemSelected
        }

        holder.bind(current, context, isSelected)
    }

    fun updateSelection(itemSelected: Int?) {
        this.itemSelected = itemSelected
        viewHolders.forEach{
            if (itemSelected != null) {
                it.updateSelection(itemSelected)
            }
        }

    }

    override fun getItemCount() = currentList.size

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
}
