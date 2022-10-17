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
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.PropertyListItemBinding
import com.openclassrooms.realestatemanager.model.House

class PropertyListAdapter : ListAdapter<House, PropertyListAdapter.PropertyViewHolder>(DiffCallback) {

    var itemSelectedPosition = -1

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //                              ViewHolder Parts
    /////////////////////////////////////////////////////////////////////////////////////////////////
    class PropertyViewHolder(private var binding: PropertyListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var context: Context

        fun bind(house: House, context: Context, position: Int, itemSelectedPosition: Int) {
            this.context = context

            binding.houseType.text = house.detailViewType
            binding.houseNeighborhood.text = house.detailViewNearTitle
            binding.housePrice.text = house.detailViewPrice


            if (position == itemSelectedPosition) {
                configureCardToSelectedState()
            } else {
                configureCardToNormalState()
            }

        }


        private fun configureCardToNormalState() {
            binding.root.setBackgroundColor(Color.WHITE)
            binding.housePrice.setTextColor(getColor(context, R.color.colorAccent))
        }

        private fun configureCardToSelectedState() {
            binding.root.setBackgroundColor(getColor(context, R.color.colorAccent))
            binding.housePrice.setTextColor(getColor(context, R.color.colorTextAccent))
        }


    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //                                     Adapter parts
    /////////////////////////////////////////////////////////////////////////////////////////////////

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        context = parent.context
        return PropertyViewHolder(PropertyListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("NotifyDataSetChanged", "ResourceAsColor")
    override fun onBindViewHolder(holder: PropertyViewHolder, @SuppressLint("RecyclerView") position: Int) {


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
                        oldItem.detailViewType == newItem.detailViewType ||
                        oldItem.detailViewNearTitle == newItem.detailViewNearTitle ||
                        oldItem.detailViewPrice == newItem.detailViewPrice
                    )
            }

            override fun areContentsTheSame(oldItem: House, newItem: House): Boolean {
                return oldItem == newItem
            }
        }
    }
}
