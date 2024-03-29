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

package com.openclassrooms.realestatemanager.propertylist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.PropertyListItemBinding
import com.openclassrooms.realestatemanager.model.House
import com.openclassrooms.realestatemanager.utils.Utils
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import java.util.*

class PropertiesListAdapter : ListAdapter<House, PropertiesListAdapter.PropertyViewHolder>(DiffCallback) {

    private lateinit var context: Context

    var itemSelectedPosition = 0

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //                                     Adapter parts
    /////////////////////////////////////////////////////////////////////////////////////////////////
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        context = parent.context
        return PropertyViewHolder(PropertyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("NotifyDataSetChanged", "ResourceAsColor")
    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.bind(getItem(position), context, position, itemSelectedPosition)
    }

    // For change the configuration when you click on a item
    @SuppressLint("NotifyDataSetChanged")
    fun updateSelection(positionSelected: Int) {
        this.itemSelectedPosition = positionSelected
        notifyDataSetChanged()
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

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //                              ViewHolder Parts
    /////////////////////////////////////////////////////////////////////////////////////////////////
    class PropertyViewHolder(private var binding: PropertyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var context: Context

        @SuppressLint("SetTextI18n")
        fun bind(house: House, context: Context, position: Int, itemSelectedPosition: Int) {
            this.context = context

            binding.houseType.text = house.detailViewType

            binding.houseNeighborhood.text = house.detailViewNearTitle

            // For price and check if euros or dollars selected
            val sharedPref = context.getSharedPreferences("CHANGE_CURRENCY", Context.MODE_PRIVATE)
            val currencyValue = sharedPref.getString("CHANGE_CURRENCY","currencyEuros")

            if (currencyValue == "currencyDollars"){
                binding.housePrice.text = "$ " + Utils.numberFormat(Utils.convertEuroToDollar(house.detailViewPrice.toInt()))
            } else if (currencyValue == "currencyEuros") {
                binding.housePrice.text = Utils.numberFormat(house.detailViewPrice.toInt()) + " €"
            }

            if (house.descriptionPictures.isNotEmpty()) {
                val listPictures = house.descriptionPictures[0]
                val picturesId = listPictures.picturesId

                binding.customViewWithLabel.setCustomViewProperties(listPictures,house.houseId,picturesId, false)

            } else {
                binding.customViewWithLabel.setPlaceHolderCustomViewProperties()
            }

            // For sold property
            if (house.detailSold) {
                Picasso.get()
                    .load(R.drawable.sold)
                    .into(binding.houseSold)
            }

            // For background color selected
            if (position == itemSelectedPosition) {
                configureCardToSelectedState()
            } else {
                configureCardToNormalState()
            }
    }

        private fun configureCardToNormalState() {
            binding.root.setBackgroundColor(Color.WHITE)
            binding.housePrice.setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
        }

        private fun configureCardToSelectedState() {
            binding.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
            binding.housePrice.setTextColor(ContextCompat.getColor(context, R.color.colorTextAccent))
        }

    }
}
