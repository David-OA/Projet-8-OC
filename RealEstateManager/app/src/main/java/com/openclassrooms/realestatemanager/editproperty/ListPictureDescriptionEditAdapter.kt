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
import com.openclassrooms.realestatemanager.databinding.ListPicturesAddedItemBinding
import com.openclassrooms.realestatemanager.model.DescriptionPictures

class ListPictureDescriptionEditAdapter (
     private val descriptionEditPictureList: MutableList<DescriptionPictures>, private val descriptionPicturesList: MutableList<DescriptionPictures>
) : ListAdapter<InternalStoragePhoto, ListPictureDescriptionEditAdapter.PhotoViewHolder>(Companion), DescriptionPicturesUi.DescriptionPictureSaved{

    inner class PhotoViewHolder(val binding: ListPicturesAddedItemBinding, descriptionPictureSaved: DescriptionPicturesUi.DescriptionPictureSaved): RecyclerView.ViewHolder(binding.root) {

        /*init {
            binding.picturesAddedRvDescription.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val description = s.toString()

                    descriptionPictureSaved.run {
                        if (adapterPosition != RecyclerView.NO_POSITION) {
                            descriptionPictureSaved.onDescriptionPictureUpdated(adapterPosition, description)
                        }
                    }
                }
            })
        }*/

        fun bind() {
            //binding.picturesAddedRvDescription.setText(descriptionPictures.description)
            val position = if (descriptionPicturesList.isEmpty()) {
                0
            } else {
                descriptionPicturesList.size -1
            }

            if (descriptionPicturesList.isNotEmpty()) {
                val descriptionSelected = descriptionPicturesList[adapterPosition]
                binding.picturesAddedRvDescription.setText(descriptionSelected.description)
            } else {
                binding.picturesAddedRvDescription.setText("C'est vide")
            }

        }
    }

    companion object : DiffUtil.ItemCallback<InternalStoragePhoto>() {
        override fun areItemsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: InternalStoragePhoto, newItem: InternalStoragePhoto): Boolean {
            return oldItem.name == newItem.name && oldItem.bmp.sameAs(newItem.bmp)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(ListPicturesAddedItemBinding.inflate(LayoutInflater.from(parent.context), parent,false), this)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photo = currentList[position]
        holder.binding.apply {
            picturesAddedRvPicture.setImageBitmap(photo.bmp)
        }

        holder.bind()
    }

    override fun onDescriptionPictureUpdated(position: Int, description: String) {
        if (descriptionEditPictureList.isNotEmpty()) {
            descriptionEditPictureList[position].description = description
        }
    }
}
