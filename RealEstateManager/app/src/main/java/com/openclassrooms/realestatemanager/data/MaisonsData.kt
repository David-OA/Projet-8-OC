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

package com.openclassrooms.realestatemanager.data

import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Maison


/**
 * Sports data
 */
object MaisonsData {
    fun getSportsData(): ArrayList<Maison> {
        return arrayListOf(
            Maison(
                id = 1,
                detailsViewDescription = "Villa contemporaine de standing de 246m² habitables & 42m2 d'annexes, avec piscine, réalisée en 2021, avec isolation aux normes RT 2020.\n" +
                        "\n" +
                        "Située dans la commune de Montanay, à proximité de tous les services et commodités, à seulement 25 minutes de Lyon (accès A46 en 5 minutes), dans un secteur calme avec vue imprenable sur les Monts d’Or.\n" +
                        "\n" +
                        "D’une surface totale de 264m2 sur une parcelle de 600m2, vous bénéficierez au rez-de-chaussée d’un bel espace de vie de 55m2 (hauteur sous plafond 2,70m) avec sa cuisine aménagée et équipée haut de gamme (cuisine Aviva avec plan de travail en Granit) bénéficiant d’une triple exposition - baie vitrée à galandage - offrant une vue agréable sur la terrasse, la piscine et le jardin. De larges ouvertures sur l’extérieur lui confèrent une luminosité optimale.\n" +
                        "\n" +
                        "Une entrée, une belle suite parentale de 23m2, des WC séparés, une buanderie et un garage de 38,3m² complètent ce niveau.\n" +
                        "\n" +
                        "L’étage comprend un espace ouvert pouvant faire office de bureau ou de salle de jeux, ainsi que 4 chambres de 16m2  lumineuses et spacieuses, toutes dotées de dressings/rangements, et d’une salle d’eau. Un WC séparé complète cet étage.\n" +
                        "\n" +
                        "Au sous-sol, nous retrouvons une belle pièce de vie de 44m2, lumineuse grâce à deux courettes anglaises, pouvant être adaptée à tous vos projets (actuellement salle de sport), ainsi qu’une salle de cinéma, un cellier et une cave.\n" +
                        "\n" +
                        "L’extérieur offre un cadre agréable avec sa terrasse en dalles antidérapantes (exposée Ouest), une belle piscine 4m x 9m et sa pool house. Nous retrouvons également un terrain de pétanque avec vue sur les Monts d’Or.\n" +
                        "\n" +
                        "La maison est équipée d'un adoucisseur d'eau et d'une pompe à chaleur de 2021, avec un système de plancher chauffant/rafraichissant sur les 3 niveaux, mais également d'un système d'eau chaude par cumulus thermodynamique. \n" +
                        "\n" +
                        "Réalisée en 2021, cette villa contemporaine vous séduira par la qualité de ses prestations.\n" +
                        "\n" +
                        "Bénéficiant d'un environnement calme et d'une belle vue sur les Monts d'Or, cette maison enchantera une famille à la recherche d'une demeure spacieuse et confortable aux portes de Lyon.",
                detailsViewSurface = "264",
                detailsViewRooms = "10",
                detailsViewBath = "5",
                detailsViewBed = "5",
                detailViewPrice = "1 284 000",
                detailViewType = "Maisons d'architecte",
                detailViewNearTitle = "Lyon",
                detailsViewSliderPictures = R.drawable.photo1,

            ),
            Maison(
                id = 2,
                detailsViewDescription = "Appartement",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Appartement",
                detailViewNearTitle = "R.string.nombre_near_title",
                detailsViewSliderPictures = R.drawable.photo2,
            ),
            Maison(
                id = 3,
                detailsViewDescription = "Villa",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Villa",
                detailViewNearTitle = "R.string.nombre_near_title",
                detailsViewSliderPictures = R.drawable.photo3,
            ),
            Maison(
                id = 4,
                detailsViewDescription = "Immeuble",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Immeuble",
                detailViewNearTitle = "R.string.nombre_near_title",
                detailsViewSliderPictures = R.drawable.photo4,
            ),
            Maison(
                id = 5,
                detailsViewDescription = "Chateau",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Chateau",
                detailViewNearTitle = "R.string.nombre_near_title",
                detailsViewSliderPictures = R.drawable.photo5,
            ),
            Maison(
                id = 6,
                detailsViewDescription = "Domaine",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Domaine",
                detailViewNearTitle = "R.string.nombre_near_title",
                detailsViewSliderPictures = R.drawable.photo6,
            ),
            Maison(
                id = 7,
                detailsViewDescription = "Ranch",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Ranch",
                detailViewNearTitle = "R.string.nombre_near_title",
                detailsViewSliderPictures = R.drawable.photo7,
            ),
            Maison(
                id = 8,
                detailsViewDescription = "",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "R.string.nombre_type",
                detailViewNearTitle = "R.string.nombre_near_title",
                detailsViewSliderPictures = R.drawable.photo8,
            ),
            Maison(
                id = 9,
                detailsViewDescription = "Duplex",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Duplex",
                detailViewNearTitle = "R.string.nombre_near_title",
                detailsViewSliderPictures = R.drawable.photo9,
            ),
            Maison(
                id = 10,
                detailsViewDescription = "Studio",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Studio",
                detailViewNearTitle = "R.string.nombre_near_title",
                detailsViewSliderPictures = R.drawable.photo10,
            ),
            Maison(
                id = 11,
                detailsViewDescription = "Loft",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Loft",
                detailViewNearTitle = "R.string.nombre_near_title",
                detailsViewSliderPictures = R.drawable.photo11,
            )
        )
    }
}
