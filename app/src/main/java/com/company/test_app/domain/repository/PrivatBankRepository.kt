package com.company.test_app.domain.repository

import com.company.test_app.data.entity.presentation.Department
import com.company.test_app.data.entity.presentation.PrivatBank
import com.company.test_app.data.entity.presentation.ResponseTso

interface PrivatBankRepository {
    suspend fun getExchange(date : String, onComplete: (PrivatBank) -> Unit, onError: (Exception) -> Unit)

    suspend fun getDepartment(onComplete: (List<Department>) -> Unit, onError: (Exception) -> Unit)

    suspend fun getTso(onComplete: (ResponseTso) -> Unit, onError: (Exception) -> Unit)
}