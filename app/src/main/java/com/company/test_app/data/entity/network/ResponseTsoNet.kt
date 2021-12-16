package com.company.test_app.data.entity.network

import com.google.gson.annotations.SerializedName

data class ResponseTsoNet(
    @SerializedName("city") val city : String?,
    @SerializedName("address") val address : String?,
    @SerializedName("devices") val devices : List<TsoNet>?,
)
