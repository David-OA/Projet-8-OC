package com.openclassrooms.realestatemanager.propertyinfos

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.number.NumberFormatter.with
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.addproperty.InternalStoragePhoto
import com.openclassrooms.realestatemanager.databinding.PictureRecyclerviewItemBinding
import com.openclassrooms.realestatemanager.model.DescriptionPictures
import com.squareup.picasso.Picasso
import java.io.File

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
        return PictureViewHolder(PictureRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {

        holder.bind()
    }

    inner class PictureViewHolder(val binding: PictureRecyclerviewItemBinding): RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SdCardPath")
        fun bind() {
            val listPictures = picturesList[adapterPosition]

            val propertyId = listPictures.houseId
            val pictureId = listPictures.picturesId

            /*Picasso.get()
                .load(File("/data/data/com.openclassrooms.realestatemanager/files/","$propertyId.$pictureId.jpg"))
                .placeholder(R.drawable.home_icon)
                .into(binding.myimage)*/

            //binding.descriptionPicture.text = listPictures.description
            binding.customViewWithLabel.setCustomViewProperties(listPictures, propertyId, pictureId, true)
        }
    }

    override fun getItemCount(): Int {
        return picturesList.size
    }
}
