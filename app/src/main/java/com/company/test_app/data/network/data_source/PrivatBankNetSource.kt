package com.company.test_app.data.network.data_source

import android.accounts.NetworkErrorException
import com.company.test_app.common.throwException
import com.company.test_app.data.entity.mapper.NetModelMapper.map
import com.company.test_app.data.entity.mapper.NetModelMapper.mapDepartmentNet
import com.company.test_app.data.entity.network.DepartmentNet
import com.company.test_app.data.entity.network.PrivatBankNet
import com.company.test_app.data.entity.network.ResponseTsoNet
import com.company.test_app.data.entity.presentation.Department
import com.company.test_app.data.entity.presentation.PrivatBank
import com.company.test_app.data.entity.presentation.ResponseTso
import com.company.test_app.data.network.services.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PrivatBankNetSource @Inject constructor(private val privatBankApiServices: ApiServices){
    fun getExchange(
        date: String,
        onComplete: (PrivatBank) -> Unit,
        onError: (Exception) -> Unit
    ) {
        privatBankApiServices.getExchange(date)
            .enqueue(object : Callback<PrivatBankNet> {
                override fun onFailure(call: Call<PrivatBankNet>, t: Throwable) {
                    onError(NetworkErrorException(t))
                }

                override fun onResponse(
                    call: Call<PrivatBankNet>,
                    response: Response<PrivatBankNet>
                ) {
                    if (response.isSuccessful ) {
                        response.body()?.let {
                            onComplete(it.map())
                        }
                    } else {
                        onError(response.code().throwException(response.message()))
                    }
                }
            })
    }

    fun getDepartment(
        onComplete: (List<Department>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        privatBankApiServices.getDepartment()
            .enqueue(object : Callback<List<DepartmentNet>> {
                override fun onFailure(call: Call<List<DepartmentNet>>, t: Throwable) {
                    onError(NetworkErrorException(t))
                }

                override fun onResponse(
                    call: Call<List<DepartmentNet>>,
                    response: Response<List<DepartmentNet>>
                ) {
                    if (response.isSuccessful ) {
                        response.body()?.let {
                            onComplete(it.mapDepartmentNet())
                        }
                    } else {
                        onError(response.code().throwException(response.message()))
                    }
                }
            })
    }

    fun getTso(
        onComplete: (ResponseTso) -> Unit,
        onError: (Exception) -> Unit
    ) {
        privatBankApiServices.getTso()
            .enqueue(object : Callback<ResponseTsoNet> {
                override fun onFailure(call: Call<ResponseTsoNet>, t: Throwable) {
                    onError(NetworkErrorException(t))
                }

                override fun onResponse(
                    call: Call<ResponseTsoNet>,
                    response: Response<ResponseTsoNet>
                ) {
                    if (response.isSuccessful ) {
                        response.body()?.let {
                            onComplete(it.map())
                        }
                    } else {
                        onError(response.code().throwException(response.message()))
                    }
                }
            })
    }
}