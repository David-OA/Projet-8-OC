package com.openclassrooms.realestatemanager

import android.view.View
import android.widget.ImageView
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {

    var imageView: ImageView = itemView!!.findViewById(R.id.myimage)
}