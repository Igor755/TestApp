package com.company.test_app.data.constant

class ApiEndpoints {
    companion object {
        //Get matches
        const val GET_EXCHANGE = "p24api/exchange_rates?json&"
        const val GET_DEPARTMENT = "p24api/pboffice?json&city=Одесса&address="
        const val GET_ATM = "p24api/infrastructure?json&tso&address=&city=Одесса"
    }
}