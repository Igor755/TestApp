package com.company.test_app.data.entity.presentation

import com.google.gson.annotations.SerializedName

data class Currency(
    val baseCurrency : String,
    val currency : String?,
    val saleRateNB : Double,
    val purchaseRateNB : Double,
    val saleRate : Double,
    val purchaseRate : Double,
)
