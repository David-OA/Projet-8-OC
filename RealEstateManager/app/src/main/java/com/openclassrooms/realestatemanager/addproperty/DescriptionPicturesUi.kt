package com.openclassrooms.realestatemanager.addproperty

interface DescriptionPicturesUi {

    interface DescriptionPictureSaved {
        fun onDescriptionPictureUpdated(position: Int, description: String)
    }
}