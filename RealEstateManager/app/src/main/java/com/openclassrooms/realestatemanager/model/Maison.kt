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

import java.io.Serializable

/**
 * Data model for each row of the RecyclerView
 */
data class Maison(
    val id: Int,
    val detailsViewDescription: String,
    val detailsViewSurface: String,
    val detailsViewRooms: String,
    val detailsViewBath: String,
    val detailsViewBed: String,
    val detailViewPrice: String,
    val detailViewType: String,
    val detailViewNearTitle: String,
    val detailsViewListPictures: Int,
    val detailsViewSliderPicture: Array<Int>,
    ):Serializable
