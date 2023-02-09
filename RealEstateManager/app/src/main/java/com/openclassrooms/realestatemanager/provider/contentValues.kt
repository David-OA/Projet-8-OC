package com.openclassrooms.realestatemanager.provider

import android.content.ContentValues
import com.openclassrooms.realestatemanager.model.DescriptionPictures
import com.openclassrooms.realestatemanager.model.House

fun propertyFromContentValues(values: ContentValues): House {

    val listDescription = listOf(DescriptionPictures("cuisine","1bb025fb-990d-448b-b856-c8dd8dcaa5bd","ce79eac8-2321-4473-b5b4-88070c03ae94",-1),
        DescriptionPictures("salon","1bb025fb-990d-448b-b856-c8dd8dcaa5bd","3bdc1dbd-f793-4862-bd9d-04a3673763e6",0))

    val property = House(values.getAsString("house_id"),values.getAsString("details_type"),values.getAsString("details_price"),
        values.getAsString("details_surface"),values.getAsString("details_room"),
        values.getAsString("details_bed"),values.getAsString("details_bath"),values.getAsBoolean("details_nearby_buses"),
        values.getAsBoolean("details_nearby_school"),values.getAsBoolean("details_nearby_playground"),
        values.getAsBoolean("details_nearby_shop"),values.getAsBoolean("details_nearby_subway"),
        values.getAsBoolean("details_nearby_park"),values.getAsString("details_description"),
        values.getAsString("details_address"),values.getAsString("details_neartitle"),
        values.getAsString("details_market_since"),values.getAsBoolean("details_sold"),
        values.getAsString("details_sold_on"),values.getAsString("details_manage_by"),
        values.getAsDouble("details_latitude"),values.getAsDouble("details_longitude"),
        listDescription
    )
    if (values.containsKey("house_id")) property.houseId = values.getAsString("house_id")
    if (values.containsKey("details_type")) property.detailViewType = values.getAsString("details_type")
    if(values.containsKey("details_price")) property.detailViewPrice = values.getAsString("details_price")
    if(values.containsKey("details_surface")) property.detailsViewSurface = values.getAsString("details_surface")
    if(values.containsKey("details_room")) property.detailsViewRooms = values.getAsString("details_room")
    if(values.containsKey("details_bed")) property.detailsViewBed = values.getAsString("details_bed")
    if(values.containsKey("details_bath")) property.detailsViewBath = values.getAsString("details_bath")
    if(values.containsKey("details_nearby_buses")) property.amenityBuses = values.getAsBoolean("details_nearby_buses")
    if(values.containsKey("details_nearby_school")) property.amenitySchool = values.getAsBoolean("details_nearby_school")
    if(values.containsKey("details_nearby_playground")) property.amenityPlayground = values.getAsBoolean("details_nearby_playground")
    if(values.containsKey("details_nearby_shop")) property.amenityShop = values.getAsBoolean("details_nearby_shop")
    if(values.containsKey("details_nearby_subway")) property.amenitySubway = values.getAsBoolean("details_nearby_subway")
    if(values.containsKey("details_nearby_park")) property.amenityPark = values.getAsBoolean("details_nearby_park")
    if(values.containsKey("details_description")) property.detailsViewDescription = values.getAsString("details_description")
    if(values.containsKey("details_address")) property.detailsAddress = values.getAsString("details_address")
    if(values.containsKey("details_neartitle")) property.detailViewNearTitle = values.getAsString("details_neartitle")
    if(values.containsKey("details_market_since")) property.detailMarketSince = values.getAsString("details_market_since")
    if(values.containsKey("details_sold")) property.detailSold = values.getAsBoolean("details_sold")
    if(values.containsKey("details_sold_on")) property.detailSoldOn = values.getAsString("details_sold_on")
    if(values.containsKey("details_manage_by")) property.detailManageBy = values.getAsString("details_manage_by")
    if(values.containsKey("details_latitude")) property.detailLatitude = values.getAsDouble("details_latitude")
    if(values.containsKey("details_longitude")) property.detailLongitude = values.getAsDouble("details_longitude")
    //if(values.containsKey("description_pictures")) property.descriptionPictures = values.getAsString("description_pictures")
    return property
}