package com.openclassrooms.realestatemanager.propertyinfos

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.model.InternalStoragePhoto
import com.openclassrooms.realestatemanager.databinding.ListPictureInfoItemBinding
import com.openclassrooms.realestatemanager.model.DescriptionPictures

class InfoDetailsAdapter(
    private val picturesList: List<DescriptionPictures>
) : RecyclerView.Adapter<InfoDetailsAdapter.PictureViewHolder>() {

    private lateinit var context:Context

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
        context = parent.context
        return PictureViewHolder(ListPictureInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {

        holder.bind(position)
    }

    inner class PictureViewHolder(val binding: ListPictureInfoItemBinding): RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SdCardPath")
        fun bind(position: Int) {
            val listPictures = picturesList[position]

            val propertyId = listPictures.houseId
            val pictureId = listPictures.picturesId

            binding.customViewWithLabel.setCustomViewProperties(listPictures, propertyId, pictureId, true)
        }
    }

    override fun getItemCount(): Int {
        return picturesList.size
    }
}
