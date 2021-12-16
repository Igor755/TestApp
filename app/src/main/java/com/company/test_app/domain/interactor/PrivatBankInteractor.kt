package com.company.test_app.domain.interactor

import com.company.test_app.data.entity.presentation.Department
import com.company.test_app.data.entity.presentation.PrivatBank
import com.company.test_app.data.entity.presentation.ResponseTso
import com.company.test_app.domain.repository.PrivatBankRepository
import javax.inject.Inject

class PrivatBankInteractor  @Inject constructor(private val privatBankRepository: PrivatBankRepository) {
    suspend fun getExchange(date : String, onComplete: (PrivatBank) -> Unit, onError: (Exception) -> Unit){
        privatBankRepository.getExchange(date, onComplete, onError)
    }

    suspend fun getDepartment(onComplete: (List<Department>) -> Unit, onError: (Exception) -> Unit) {
         privatBankRepository.getDepartment(onComplete, onError)
    }

    suspend fun getTso(onComplete: (ResponseTso) -> Unit, onError: (Exception) -> Unit){
        privatBankRepository.getTso(onComplete, onError)
    }

}