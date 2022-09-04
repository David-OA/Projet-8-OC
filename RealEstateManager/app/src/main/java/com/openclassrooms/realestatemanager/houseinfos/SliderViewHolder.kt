package com.openclassrooms.realestatemanager.houseinfos

import android.view.View
import android.widget.ImageView
import com.openclassrooms.realestatemanager.R
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {

    var imageView: ImageView = itemView!!.findViewById(R.id.myimage)
}