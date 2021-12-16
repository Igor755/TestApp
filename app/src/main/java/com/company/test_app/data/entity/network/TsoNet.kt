package com.company.test_app.data.entity.network

import com.google.gson.annotations.SerializedName

data class TsoNet(
    @SerializedName("type") val type : String,
    @SerializedName("cityRU") val cityRU : String,
    @SerializedName("cityUA") val cityUA : String,
    @SerializedName("cityEN") val cityEN : String,
    @SerializedName("fullAddressRu") val fullAddressRu : String,
    @SerializedName("fullAddressUa") val fullAddressUa : String,
    @SerializedName("fullAddressEn") val fullAddressEn : String,
    @SerializedName("placeRu") val placeRu : String,
    @SerializedName("placeUa") val placeUa : String,
    @SerializedName("latitude") val latitude : String,
    @SerializedName("longitude") val longitude : String,
    @SerializedName("tw") val week : WeekNet)
