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
import com.openclassrooms.realestatemanager.model.House


/**
 * Sports data
 */
object HousePictureData {
    /*fun getHouseData(): ArrayList<House> {
        return arrayListOf(
            House(
                houseId = 1,
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
                amenityBuses = true
                /*detailsViewListPictures = R.drawable.photo1,
                detailsViewSliderPicture = arrayOf(R.drawable.photo1,
                    R.drawable.photo2,
                    R.drawable.photo3,
                    R.drawable.photo4,
                    R.drawable.photo5,
                    R.drawable.photo6,
                    R.drawable.photo7,
                    R.drawable.photo8,
                    R.drawable.photo9,
                    R.drawable.photo10)*/

            ),
            House(
                houseId = 2,
                detailsViewDescription = "Appartement Type 3 de 62 m² (avenue du Général de Gaulle - Cadaujac), orienté Sud/Ouest proche des commerces, écoles et transport (gare à 1 mn en voiture et tram C à 6 en voiture).\n" +
                        "\n" +
                        "Appartement lumineux dans une petite copropriété, quartier calme !\n" +
                        "\n" +
                        "Situé en Rdc/1 d'une résidence, il se compose d'une pièce de vie avec sa cuisine ouverte, aménagée, équipée ouvrant sur la terrasse et jardin0\n" +
                        "\n" +
                        "L'espace nuit comprend 2 chambres avec placard, une salle de bains et un wc indépendant.\n" +
                        "\n" +
                        "Vous profiterez d'un jardin privatif de 150 m² et d'une terrasse, idéal pour recevoir famille et amis pour des moments conviviaux.\n" +
                        "\n" +
                        "Un jardinier prend soin des espaces verts de la résidence.\n" +
                        "\n" +
                        "Immeuble sécurisé par digicode et portail automatique.\n" +
                        "\n" +
                        "Deux places de parking privatives complètent ce bien.",
                detailsViewSurface = "62",
                detailsViewRooms = "4",
                detailsViewBath = "1",
                detailsViewBed = "2",
                detailViewPrice = "256 500",
                detailViewType = "Appartement",
                detailViewNearTitle = "Cadaujac",
                amenityBuses = true
                /*detailsViewListPictures = R.drawable.photo2,
                detailsViewSliderPicture = arrayOf(R.drawable.photo1,
                    R.drawable.photo2,
                    R.drawable.photo3,
                    R.drawable.photo4,
                    R.drawable.photo5,
                    R.drawable.photo6,
                    R.drawable.photo7,
                    R.drawable.photo8,
                    R.drawable.photo9,
                    R.drawable.photo10)*/
            ),
            House(
                houseId = 3,
                detailsViewDescription = "Villa (Avenue de Gaujacq, Soorts-Hossegor) entre lac et océan. A 5min du centre ville, 2 min du canal et 5min de l'océan à pied. \n" +
                        "\n" +
                        "La maison de plain pied est composée d'un salon donnant sur cuisine, salle à manger et un WC séparé. Les pièces principales de la maison sont traversantes et donnent sur 2 grandes terrasses (300 m2).\n" +
                        "\n" +
                        "La maison comprend une suite parentale avec salle d'eau attenante, une pièce équipée en salle TV et 3 chambres (avec 2 salle d'eau).\n" +
                        "\n" +
                        "A l'exterieur, sur un terrain de 1010m² vous trouverez une piscine ( 4mx8m au sel avec système autonome et couverture rigide), une dépendance en bois 19 m2 et un container de 18m² contenant une cuisine d'été (Plancha, friteuses, tireuses à biere...). Un boulodrome et un coin Ping-Pong complètent l'exterieur pour de bons moments en familles/amis. ",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "10",
                detailsViewBath = "3",
                detailsViewBed = "5",
                detailViewPrice = "2 750 000",
                detailViewType = "Villa",
                detailViewNearTitle = "CapBreton",
                amenityBuses = true
                /*detailsViewListPictures = R.drawable.photo3,
                detailsViewSliderPicture = arrayOf(R.drawable.photo1,
                    R.drawable.photo2,
                    R.drawable.photo3,
                    R.drawable.photo4,
                    R.drawable.photo5,
                    R.drawable.photo6,
                    R.drawable.photo7,
                    R.drawable.photo8,
                    R.drawable.photo9,
                    R.drawable.photo10)*/
            ),
            House(
                houseId = 4,
                detailsViewDescription = "Immeuble",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Immeuble",
                detailViewNearTitle = "R.string.nombre_near_title",
                amenityBuses = true
                /*detailsViewListPictures = R.drawable.photo4,
                detailsViewSliderPicture = arrayOf(R.drawable.photo1,
                    R.drawable.photo2,
                    R.drawable.photo3,
                    R.drawable.photo4,
                    R.drawable.photo5,
                    R.drawable.photo6,
                    R.drawable.photo7,
                    R.drawable.photo8,
                    R.drawable.photo9,
                    R.drawable.photo10)*/
            ),
            House(
                houseId = 5,
                detailsViewDescription = "Chateau",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Chateau",
                detailViewNearTitle = "R.string.nombre_near_title",
                amenityBuses = true
                /*detailsViewListPictures = R.drawable.photo5,
                detailsViewSliderPicture = arrayOf(R.drawable.photo1,
                    R.drawable.photo2,
                    R.drawable.photo3,
                    R.drawable.photo4,
                    R.drawable.photo5,
                    R.drawable.photo6,
                    R.drawable.photo7,
                    R.drawable.photo8,
                    R.drawable.photo9,
                    R.drawable.photo10)*/
            ),
            House(
                houseId = 6,
                detailsViewDescription = "Domaine",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Domaine",
                detailViewNearTitle = "R.string.nombre_near_title",
                amenityBuses = true
                /*detailsViewListPictures = R.drawable.photo6,
                detailsViewSliderPicture = arrayOf(R.drawable.photo1,
                    R.drawable.photo2,
                    R.drawable.photo3,
                    R.drawable.photo4,
                    R.drawable.photo5,
                    R.drawable.photo6,
                    R.drawable.photo7,
                    R.drawable.photo8,
                    R.drawable.photo9,
                    R.drawable.photo10)*/
            ),
            House(
                houseId = 7,
                detailsViewDescription = "Ranch",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Ranch",
                detailViewNearTitle = "R.string.nombre_near_title",
                amenityBuses = true
                /*detailsViewListPictures = R.drawable.photo7,
                detailsViewSliderPicture = arrayOf(R.drawable.photo1,
                    R.drawable.photo2,
                    R.drawable.photo3,
                    R.drawable.photo4,
                    R.drawable.photo5,
                    R.drawable.photo6,
                    R.drawable.photo7,
                    R.drawable.photo8,
                    R.drawable.photo9,
                    R.drawable.photo10)*/
            ),
            House(
                houseId = 8,
                detailsViewDescription = "",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "R.string.nombre_type",
                detailViewNearTitle = "R.string.nombre_near_title",
                amenityBuses = true
                /*detailsViewListPictures = R.drawable.photo8,
                detailsViewSliderPicture = arrayOf(R.drawable.photo1,
                    R.drawable.photo2,
                    R.drawable.photo3,
                    R.drawable.photo4,
                    R.drawable.photo5,
                    R.drawable.photo6,
                    R.drawable.photo7,
                    R.drawable.photo8,
                    R.drawable.photo9,
                    R.drawable.photo10)*/
            ),
            House(
                houseId = 9,
                detailsViewDescription = "Appartement duplex T3 de 155 m² avec penthouse et balcons, orienté Ouest, accès direct Bois de Vincennes (Avenue de la Belle Gabrielle), proche commerces et transports (5 min à pieds du RER A Nogent-sur-Marne, lignes de bus 120 et 113).\n" +
                        "\n" +
                        "La grande terrasse sur le toit de 86m² et les deux balcons de 16 et 12m² bénéficient de mutiples expositions qui permettent de profiter du soleil tout au long de la journée! Le bois de Vincennes est directement accessible depuis l'entrée de la copropriété.\n" +
                        "\n" +
                        "Copropriété calme et bien entretenue avec gardien.\n" +
                        "\n" +
                        "Situé au 3ème et 4ème étages/4 d'un immeuble avec ascenseur, il se compose au Rdc d'une entrée, d'un salon avec cheminée, une salle à manger et une cuisine fermée, aménagée et équipée. Le coin nuit se compose d'une première chambre, d'une salle d'eau, d'un wc séparé avec lave-mains et d'une suite parentale comprenant une salle de bains avec jacuzzi et douche, des grands placards et un wc independant. Deux balcons filants orientés Ouest et Sud viennent compléter cet étage. \n" +
                        "\n" +
                        "Au 1er niveau se trouve une salle d'eau avec wc et un bel espace ouvert de 25m² pouvant accueillir une chambre supplémentaire. Un espace coin cuisine d'été avec accès direct à la terrasse est également présent sur ce niveau.\n" +
                        "\n" +
                        "Une cave et un box en sous-sol complètent ce bien.",
                detailsViewSurface = "155",
                detailsViewRooms = "7",
                detailsViewBath = "3",
                detailsViewBed = "2",
                detailViewPrice = "1 649 000",
                detailViewType = "Duplex",
                detailViewNearTitle = "Nogent-sur-Marne",
                amenityBuses = true
                /*detailsViewListPictures = R.drawable.photo9,
                detailsViewSliderPicture = arrayOf(R.drawable.photo1,
                    R.drawable.photo2,
                    R.drawable.photo3,
                    R.drawable.photo4,
                    R.drawable.photo5,
                    R.drawable.photo6,
                    R.drawable.photo7,
                    R.drawable.photo8,
                    R.drawable.photo9,
                    R.drawable.photo10)*/
            ),
            House(
                houseId = 10,
                detailsViewDescription = "Studio",
                detailsViewSurface = "R.string.baseball_subtitle",
                detailsViewRooms = "R.string.rooms",
                detailsViewBath = "R.string.bath",
                detailsViewBed = "R.string.rooms",
                detailViewPrice = "R.string.price",
                detailViewType = "Studio",
                detailViewNearTitle = "R.string.nombre_near_title",
                amenityBuses = true
                /*detailsViewListPictures = R.drawable.photo10,
                detailsViewSliderPicture = arrayOf(R.drawable.photo1,
                    R.drawable.photo2,
                    R.drawable.photo3,
                    R.drawable.photo4,
                    R.drawable.photo5,
                    R.drawable.photo6,
                    R.drawable.photo7,
                    R.drawable.photo8,
                    R.drawable.photo9,
                    R.drawable.photo10)*/
            ),
            House(
                houseId = 11,
                detailsViewDescription = "Loft de 225 m² avec terrasse de 96 m² à Bordeaux sans vis à vis (Chartrons - Rue Delord).\n" +
                        "\n" +
                        "Au rez de chaussée se trouvent un espace de vie de 72 m² comprenant une cuisine américaine dinatoire et son salon.\n" +
                        "\n" +
                        "Vous y trouverez également une suite parentale, un wc séparé, un cellier ainsi qu'une buanderie. \n" +
                        "\n" +
                        "Le premier étage se compose d'un bureau de 10.6 m² et de 4 chambres (12 à 13m²) ainsi qu'une salle de bains.\n" +
                        "\n" +
                        "En été, vous pourrez vous prélasser au bord de la piscine (7x3 - Semi-Enterrée) et profiter pleinement de la terrasse en toute tranquilité.\n" +
                        "\n" +
                        "Vous disposerez de 2 places de parking securisées. ",
                detailsViewSurface = "225",
                detailsViewRooms = "9",
                detailsViewBath = "2",
                detailsViewBed = "5",
                detailViewPrice = "1 299 500",
                detailViewType = "Loft",
                detailViewNearTitle = "Bordeaux",
                amenityBuses = true

                /*detailsViewListPictures = R.drawable.photo11,
                detailsViewSliderPicture = arrayOf(R.drawable.photo1,
                    R.drawable.photo2,
                    R.drawable.photo3,
                    R.drawable.photo4,
                    R.drawable.photo5,
                    R.drawable.photo6,
                    R.drawable.photo7,
                    R.drawable.photo8,
                    R.drawable.photo9,
                    R.drawable.photo10)*/
            )
        )
    }*/
}
