package com.openclassrooms.realestatemanager

import android.content.ContentValues
import com.openclassrooms.realestatemanager.model.DescriptionPictures
import com.openclassrooms.realestatemanager.utils.TypeProperty

fun generatePropertyAndDataContentValue(houseId: String): ContentValues {

    val listDescription = listOf(
        DescriptionPictures("cuisine","1bb025fb-990d-448b-b856-c8dd8dcaa5bd","ce79eac8-2321-4473-b5b4-88070c03ae94",-1),
        DescriptionPictures("salon","1bb025fb-990d-448b-b856-c8dd8dcaa5bd","3bdc1dbd-f793-4862-bd9d-04a3673763e6",0)
    )

    val value = ContentValues()
    value.put("house_id",houseId)
    value.put("details_type", TypeProperty.HOUSE.typeName)
    value.put("details_price", "100000")
    value.put("details_surface", "250")
    value.put("details_room", "10")
    value.put("details_bed", "5")
    value.put("details_bath", "2")
    value.put("details_nearby_buses", true)
    value.put("details_nearby_school", true)
    value.put("details_nearby_playground", true)
    value.put("details_nearby_shop", true)
    value.put("details_nearby_subway", true)
    value.put("details_nearby_park", true)
    value.put("details_description", "Jolie maison de ville")
    value.put("details_address", "35 rte du temps")
    value.put("details_neartitle", "Paradis")
    value.put("details_market_since", "01/01/2023")
    value.put("details_sold", false)
    value.put("details_sold_on", "")
    value.put("details_manage_by", "Johny blanc")
    value.put("details_latitude", 44.38110)
    value.put("details_longitude", 0.69525)
    value.put("description_pictures", "")
    return value
}