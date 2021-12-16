package com.company.test_app.data.entity.presentation

data class PrivatBank(
    val date : String,
    val bank : String,
    val baseCurrency : Int,
    val baseCurrencyLit : String,
    var exchangeRate : List<Currency>
)
