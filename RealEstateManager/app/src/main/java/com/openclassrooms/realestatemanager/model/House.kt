/*
 * Copyright (c) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.openclassrooms.realestatemanager.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable








/**
 * Data model for each row of the RecyclerView
 */
@Entity(tableName = "house")
data class House(
    @ColumnInfo(name = "house_id") @PrimaryKey var houseId: Int,
    @ColumnInfo(name = "details_type") var detailViewType: String,
    @ColumnInfo(name = "details_price") var detailViewPrice: String,
    @ColumnInfo(name = "details_surface") var detailsViewSurface: String,
    @ColumnInfo(name = "details_room") var detailsViewRooms: String,
    @ColumnInfo(name = "details_bed") var detailsViewBed: String,
    @ColumnInfo(name = "details_bath") var detailsViewBath: String,
    @ColumnInfo(name = "details_nearby_buses") var amenityBuses: Boolean,
    @ColumnInfo(name = "details_nearby_school") var amenitySchool: Boolean,
    @ColumnInfo(name = "details_nearby_playground") var amenityPlayground: Boolean,
    @ColumnInfo(name = "details_nearby_shop") var amenityShop: Boolean,
    @ColumnInfo(name = "details_nearby_subway") var amenitySubway: Boolean,
    @ColumnInfo(name = "details_nearby_park") var amenityPark: Boolean,
    @ColumnInfo(name = "details_description") var detailsViewDescription: String,
    @ColumnInfo(name = "details_address") var detailsAddress: String,
    @ColumnInfo(name = "details_neartitle") var detailViewNearTitle: String,
    @ColumnInfo(name = "details_market_since") var detailMarketSince: String,
    @ColumnInfo(name = "details_sold") var detailSold: Boolean,
    @ColumnInfo(name = "details_sold_on") var detailSoldOn: String,
    @ColumnInfo(name = "details_manage_by") var detailManageBy: String
):Serializable