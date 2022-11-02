package com.openclassrooms.realestatemanager.editproperty

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.addproperty.DescriptionPicturesUi
import com.openclassrooms.realestatemanager.addproperty.InternalStoragePhoto
import com.openclassrooms.realestatemanager.addproperty.ListPictureDescriptionAdapter
import com.openclassrooms.realestatemanager.databinding.ListPicturesAddedItemBinding
import com.openclassrooms.realestatemanager.model.DescriptionPictures

class ListPictureDescriptionEditAdapter (
     private val descriptionPicturesList: MutableList<DescriptionPictures>
) : ListAdapter<InternalStoragePhoto, ListPictureDescriptionEditAdapter.PhotoViewHolder>(Companion)/*, DescriptionPicturesUi.DescriptionPictureSaved*/{

    private var descriptionPictureList: MutableList<DescriptionPictures> = mutableListOf()

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
        val photo = currentList[position]
        holder.binding.apply {
            picturesAddedRvPicture.setImageBitmap(photo.bmp)
        }

        holder.bind()
    }

    // For add a description in the list
    fun addPicturesDescription(position: Int, description:String, houseId: String, picturesId: String) {
        if (descriptionPictureList.getOrNull(position) == null) {
            descriptionPictureList.add(DescriptionPictures(description,houseId, picturesId))
            notifyItemInserted(descriptionPictureList.size -1)
            notifyDataSetChanged()
        } else {
            descriptionPictureList[position] = DescriptionPictures(description,houseId, picturesId)
            notifyItemChanged(descriptionPictureList.size -1)
            notifyDataSetChanged()
        }
    }

    fun getTheListofDescriptionPictures(): List<DescriptionPictures> {
        val descriptions = descriptionPictureList
        return descriptions
    }

    inner class PhotoViewHolder(val binding: ListPicturesAddedItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            if (descriptionPicturesList.isNotEmpty()) {
                val descriptionPictureFromAlertDialog = descriptionPicturesList[adapterPosition]
                binding.picturesAddedTextview.setText(descriptionPictureFromAlertDialog.description)
            } else {
                binding.picturesAddedTextview.setText("C'est vide")
            }
        }
    }
}
