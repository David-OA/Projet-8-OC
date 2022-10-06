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
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.addproperty.InternalStoragePhoto
import com.openclassrooms.realestatemanager.databinding.PropertyListItemBinding
import com.openclassrooms.realestatemanager.model.House
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HouseListAdapter(private val onItemClicked: (House) -> Unit) :
    ListAdapter<House, HouseListAdapter.PropertyViewHolder>(DiffCallback) {




    private lateinit var context: Context

    private val viewHolders = mutableListOf<PropertyViewHolder>()

    private var currentSelection = 0


    // ViewHolder Parts
    class PropertyViewHolder(private var binding: PropertyListItemBinding, val  onItemClicked: (House) -> Unit) :
        RecyclerView.ViewHolder(binding.root), CoroutineScope {

        private lateinit var houseIdList: String

        private lateinit var context: Context

        private var job: Job = Job()

        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + job

        fun bind(house: House, context: Context, position: Int, selected: Boolean, function: (Int) -> Unit) {
            this.context = context

            binding.houseType.text = house.detailViewType
            binding.houseNeighborhood.text = house.detailViewNearTitle
            binding.housePrice.text = house.detailViewPrice

            houseIdList = house.houseId

            launch {
                val photo = loadPhotosFromInternalStorage()
                if (photo.isNotEmpty()) {
                    binding.houseImage.setImageBitmap(photo.first().bmp)
                } else {
                    binding.houseImage.setImageResource(R.drawable.home_icon)
                }
            }


            itemView.isSelected = selected

            itemView.setOnClickListener {
                function(position)
            }

        }


        private suspend fun loadPhotosFromInternalStorage(): List<InternalStoragePhoto> {
            return withContext(Dispatchers.IO) {
                val files = context.filesDir?.listFiles()

                files?.filter { it.canRead() && it.isFile && it.name.endsWith(".jpg") && it.name.startsWith(houseIdList) }?.map {
                    val bytes = it.readBytes()
                    val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    InternalStoragePhoto(it.name,bmp)
                } ?: listOf()
            }
        }



    }

    // Adapter parts
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.property_list_item, parent, false)
        val holder = PropertyViewHolder(PropertyListItemBinding.bind(view), onItemClicked)
        viewHolders.add(holder)
        return holder
    }



    @SuppressLint("NotifyDataSetChanged", "ResourceAsColor")
    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val current = getItem(position)

        holder.itemView.setOnClickListener {
            onItemClicked(current)

        }

        val function = { pos: Int ->
            if (currentSelection != pos) {
                currentSelection = pos
                notifyDataSetChanged()
            }

        }

        holder.bind(current, context, position, position  == currentSelection, function)
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
