package com.company.test_app.data.entity.presentation

data class Tso(
    val type: String,
    val cityRU: String,
    val cityUA: String,
    val cityEN: String,
    val fullAddressRu: String,
    val fullAddressUa: String,
    val fullAddressEn: String,
    val placeRu: String,
    val placeUa: String,
    val latitude: String,
    val longitude: String,
    val week: Week
)
