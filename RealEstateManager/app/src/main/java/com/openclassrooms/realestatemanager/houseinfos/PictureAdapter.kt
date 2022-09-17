package com.openclassrooms.realestatemanager.houseinfos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.squareup.picasso.Picasso

class PictureAdapter(imageUrl: Array<Int>) : RecyclerView.Adapter<PictureViewHolder>() {

    var pictureList: Array<Int> = imageUrl

    override fun getItemCount(): Int {
        return pictureList.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.picture_recyclerview_item, null)
        return PictureViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
            // if view holder is not null we are simply
            // loading the image inside our image view using glide library
            Picasso.get()
                .load(pictureList[position])
                .into(holder.imageView)
    }
}