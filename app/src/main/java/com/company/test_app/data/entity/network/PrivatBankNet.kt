package com.company.test_app.data.entity.network

import com.google.gson.annotations.SerializedName

data class PrivatBankNet(
    @SerializedName("date") val date : String,
    @SerializedName("bank") val bank : String,
    @SerializedName("baseCurrency") val baseCurrency : Int,
    @SerializedName("baseCurrencyLit") val baseCurrencyLit : String,
    @SerializedName("exchangeRate") val exchangeRate : List<CurrencyNet>
)
