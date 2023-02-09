package com.openclassrooms.realestatemanager.editproperty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.model.InternalStoragePhoto
import com.openclassrooms.realestatemanager.databinding.ListPicturesAddedItemBinding

class ListPictureDescriptionEditAdapter(
     private val picturesListEdit: MutableList<InternalStoragePhoto>
) : RecyclerView.Adapter<ListPictureDescriptionEditAdapter.PhotoViewHolder>(){

    companion object : DiffUtil.ItemCallback<InternalStoragePhoto>() {
        override fun areItemsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto): Boolean {
            return oldItem.name == newItem.name && oldItem.bmp.sameAs(newItem.bmp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(ListPicturesAddedItemBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class PhotoViewHolder(private val binding: ListPicturesAddedItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(position: Int) {
            val descriptionPictureFromAlertDialog = picturesListEdit[position]

            binding.picturesAddedTextview.text = descriptionPictureFromAlertDialog.description

            binding.picturesAddedRvPicture.setImageBitmap(descriptionPictureFromAlertDialog.bmp)

        }
    }

    override fun getItemCount(): Int {
        return picturesListEdit.size
    }
}
