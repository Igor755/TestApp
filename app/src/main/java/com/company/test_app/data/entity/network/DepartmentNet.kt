package com.company.test_app.data.entity.network

import com.google.gson.annotations.SerializedName

class DepartmentNet (
    @SerializedName("country") val country : String,
    @SerializedName("state") val state : String,
    @SerializedName("city") val city : String,
    @SerializedName("index") val index : String,
    @SerializedName("address") val address : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("email") val email : String,
    @SerializedName("name") val name : String
)