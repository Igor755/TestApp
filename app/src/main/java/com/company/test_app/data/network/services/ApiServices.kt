package com.company.test_app.data.network.services

import com.company.test_app.data.constant.ApiEndpoints
import com.company.test_app.data.entity.network.DepartmentNet
import com.company.test_app.data.entity.network.PrivatBankNet
import com.company.test_app.data.entity.network.ResponseTsoNet
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET(ApiEndpoints.GET_EXCHANGE)
    fun getExchange(@Query("date")  date : String): Call<PrivatBankNet>

    @GET(ApiEndpoints.GET_DEPARTMENT)
    fun getDepartment(): Call<List<DepartmentNet>>

    @GET(ApiEndpoints.GET_ATM)
    fun getTso(): Call<ResponseTsoNet>
}