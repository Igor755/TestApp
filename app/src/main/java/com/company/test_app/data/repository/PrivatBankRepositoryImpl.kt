package com.company.test_app.data.repository

import com.company.test_app.data.entity.presentation.Department
import com.company.test_app.data.entity.presentation.PrivatBank
import com.company.test_app.data.entity.presentation.ResponseTso
import com.company.test_app.data.network.data_source.PrivatBankNetSource
import com.company.test_app.domain.repository.PrivatBankRepository
import javax.inject.Inject

class PrivatBankRepositoryImpl @Inject constructor(private val privatBankNetSource: PrivatBankNetSource) : PrivatBankRepository {
    override suspend fun getExchange(date : String, onComplete: (PrivatBank) -> Unit, onError: (Exception) -> Unit) {
        privatBankNetSource.getExchange(date, onComplete, onError)
    }

    override suspend fun getDepartment(onComplete: (List<Department>) -> Unit, onError: (Exception) -> Unit) {
        privatBankNetSource.getDepartment(onComplete, onError)
    }

    override suspend fun getTso(onComplete: (ResponseTso) -> Unit, onError: (Exception) -> Unit) {
        privatBankNetSource.getTso(onComplete, onError)
    }
}