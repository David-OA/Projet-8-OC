package com.openclassrooms.realestatemanager.addproperty

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.ListPicturesAddedItemBinding

class ListPictureDescriptionAdapter (
    val onPhotoClick: (InternalStoragePhoto) -> Unit
) : ListAdapter<InternalStoragePhoto, ListPictureDescriptionAdapter.PhotoViewHolder>(Companion) {

    inner class PhotoViewHolder(val binding: ListPicturesAddedItemBinding): RecyclerView.ViewHolder(binding.root)

    companion object : DiffUtil.ItemCallback<InternalStoragePhoto>() {
        override fun areItemsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto): Boolean {
            return oldItem.name == newItem.name && oldItem.bmp.sameAs(newItem.bmp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ListPicturesAddedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = currentList[position]
        holder.binding.apply {
            picturesAddedRvPicture.setImageBitmap(photo.bmp)

            /*val aspectRatio = photo.bmp.width.toFloat() / photo.bmp.height.toFloat()
            ConstraintSet().apply {
                clone(root)
                setDimensionRatio(picturesAddedRvPicture.id, aspectRatio.toString())
                applyTo(root)
            }*/

            picturesAddedRvPicture.setOnLongClickListener {
                onPhotoClick(photo)
                true
            }
        }
    }
}