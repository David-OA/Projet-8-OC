package com.openclassrooms.realestatemanager.propertyinfos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.addproperty.InternalStoragePhoto
import com.openclassrooms.realestatemanager.databinding.PictureRecyclerviewItemBinding

class PictureAdapter(
    val pictureList: (InternalStoragePhoto) -> Unit
) : ListAdapter<InternalStoragePhoto, PictureAdapter.PictureViewHolder>(Companion) {

    inner class PictureViewHolder(val binding: PictureRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<InternalStoragePhoto>() {
        override fun areItemsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto): Boolean {
            return oldItem.bmp == newItem.bmp
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto): Boolean {
            return oldItem.name == newItem.name && oldItem.bmp.sameAs(newItem.bmp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        return PictureViewHolder(
            PictureRecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val photo = currentList[position]
        holder.binding.apply {
            myimage.setImageBitmap(photo.bmp)

        }
    }
}