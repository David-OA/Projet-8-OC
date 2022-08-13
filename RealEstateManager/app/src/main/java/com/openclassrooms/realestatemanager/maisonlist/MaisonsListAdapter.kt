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

package com.openclassrooms.realestatemanager.maisonlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.MaisonsListItemBinding
import com.openclassrooms.realestatemanager.model.Maison

class MaisonsListAdapter(private val onItemClicked: (Maison) -> Unit) :
    ListAdapter<Maison, MaisonsListAdapter.MaisonsViewHolder>(DiffCallback) {

    private lateinit var context: Context


    class MaisonsViewHolder(private var binding: MaisonsListItemBinding, val  onItemClicked: (Maison) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(maison: Maison, context: Context) {
            binding.houseType.text = maison.detailViewType
            binding.houseNeighborhood.text = maison.detailViewNearTitle
            binding.housePrice.text = maison.detailViewPrice
            binding.houseImage.load(maison.detailsViewSliderPictures)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaisonsViewHolder {
        context = parent.context
        //return MaisonsViewHolder(MaisonsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), onItemClicked
        //)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.maisons_list_item, parent, false)
        return MaisonsViewHolder(MaisonsListItemBinding.bind(view), onItemClicked)
    }

    override fun onBindViewHolder(holder: MaisonsViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current, context)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Maison>() {
            override fun areItemsTheSame(oldItem: Maison, newItem: Maison): Boolean {
                return (
                    oldItem.id == newItem.id ||
                        oldItem.detailsViewDescription == newItem.detailsViewDescription ||
                        oldItem.detailsViewSurface == newItem.detailsViewSurface
                    )
            }

            override fun areContentsTheSame(oldItem: Maison, newItem: Maison): Boolean {
                return oldItem == newItem
            }
        }
    }
}
