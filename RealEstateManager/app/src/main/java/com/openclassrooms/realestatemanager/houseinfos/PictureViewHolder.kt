package com.openclassrooms.realestatemanager.houseinfos

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.smarteist.autoimageslider.SliderViewAdapter

class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var imageView: ImageView = itemView.findViewById(R.id.myimage)
}