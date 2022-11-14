package com.openclassrooms.realestatemanager.addproperty

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.databinding.ListPicturesAddedItemBinding
import com.openclassrooms.realestatemanager.model.DescriptionPictures

class ListPictureDescriptionAdapter (
    private val picturesList: MutableList<InternalStoragePhoto>
) : RecyclerView.Adapter<ListPictureDescriptionAdapter.PhotoViewHolder>(){

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
        val context = holder.binding.picturesAddedRvPicture.context

        holder.bind(context)
    }

    // For add a description in the list
    @SuppressLint("NotifyDataSetChanged")
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

    fun getTheListOfDescriptionPicturesInTheAdapter(): List<DescriptionPictures> {
        return descriptionPictureList
    }

    inner class PhotoViewHolder(val binding: ListPicturesAddedItemBinding): RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "SdCardPath")
        fun bind(context: Context) {
            val descriptionPictureFromAlertDialog = picturesList[adapterPosition]

            binding.picturesAddedTextview.text =  descriptionPictureFromAlertDialog.description

            val namePhoto = descriptionPictureFromAlertDialog.name

            /*val path = "/data/data/com.openclassrooms.realestatemanager/files"//context.filesDir.absolutePath//Paths.get("/data/data/com.openclassrooms.realestatemanager/files")

            Picasso.get()
                .load(File("$path/$namePhoto.jpg"))
                //.placeholder(R.drawable.home_icon)
                .into(binding.picturesAddedRvPicture)*/

            binding.picturesAddedRvPicture.setImageBitmap(descriptionPictureFromAlertDialog.bmp)
        }
    }

    override fun getItemCount(): Int {
        return picturesList.size
    }
}
