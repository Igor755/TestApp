package com.company.test_app.data.entity.network

import com.google.gson.annotations.SerializedName

data class CurrencyNet(
    @SerializedName("baseCurrency") val baseCurrency : String,
    @SerializedName("currency") val currency : String?,
    @SerializedName("saleRateNB") val saleRateNB : Double,
    @SerializedName("purchaseRateNB") val purchaseRateNB : Double,
    @SerializedName("saleRate") val saleRate : Double,
    @SerializedName("purchaseRate") val purchaseRate : Double,
)
