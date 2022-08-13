package com.openclassrooms.realestatemanager.maisoninfos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.openclassrooms.realestatemanager.R
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class SliderAdapter(imageUrl: Array<Int>) : SliderViewAdapter<SliderViewHolder>() {

    var sliderList: Array<Int> = imageUrl

    override fun getCount(): Int {
        return sliderList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val inflate: View = LayoutInflater.from(parent!!.context).inflate(R.layout.slider_item, null)
        return SliderViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        if (viewHolder != null) {
            // if view holder is not null we are simply
            // loading the image inside our image view using glide library
            Picasso.get()
                .load(sliderList[position])
                .into(viewHolder.imageView)

                //.with(viewHolder.itemView).load(sliderList.get(position)).fitCenter()
                //.into(viewHolder.imageView)
        }
    }
}