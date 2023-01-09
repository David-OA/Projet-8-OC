package com.openclassrooms.realestatemanager.propertyinfos

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.DescriptionPictures
import com.squareup.picasso.Picasso
import java.io.File

class ImageViewWithLabel (
    context: Context,
    attrs: AttributeSet? = null
) : CoordinatorLayout(context, attrs) {

    private var descriptionView: TextView
    private var imageView: ImageView


    init {
        val customView = LayoutInflater
            .from(context).inflate(R.layout.image_view_with_label, this, false)
        this.addView(customView)

        imageView = findViewById(R.id.picture_info_rv)

        descriptionView = findViewById(R.id.description_picture)
    }

    fun setCustomViewProperties(picturesList: DescriptionPictures, propertyId:String, pictureId:String, visible: Boolean) {
        if (visible) {
            descriptionView.text = picturesList.description
        } else {
            descriptionView.isVisible = false
        }

        Picasso.get()
            .load(File("/data/data/com.openclassrooms.realestatemanager/files/","$propertyId.$pictureId.jpg"))
            .placeholder(R.drawable.home_icon)
            .into(imageView)

    }

    fun setPlaceHolderCustomViewProperties() {
        Picasso.get().load(R.drawable.home_icon)
            .into(imageView)
    }

}