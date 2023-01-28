package com.openclassrooms.realestatemanager.editproperty

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.addproperty.InternalStoragePhoto
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
        holder.bind()
    }

    inner class PhotoViewHolder(private val binding: ListPicturesAddedItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val descriptionPictureFromAlertDialog = picturesListEdit[adapterPosition]

            binding.picturesAddedTextview.text = descriptionPictureFromAlertDialog.description

            binding.picturesAddedRvPicture.setImageBitmap(descriptionPictureFromAlertDialog.bmp)

            /*val listDescription = picturesListEdit[adapterPosition]

            binding.picturesAddedTextview.text = listDescription.description

            val propertyId = listDescription.houseId
            val pictureId = listDescription.picturesId

            Picasso.get()
                .load(File("/data/data/com.openclassrooms.realestatemanager/files/","$propertyId.$pictureId.jpg"))
                .placeholder(R.drawable.home_icon)
                .into(binding.picturesAddedRvPicture)*/

        }
    }

    override fun getItemCount(): Int {
        return picturesListEdit.size
    }
}
