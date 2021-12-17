package com.company.test_app.presentation.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.company.test_app.common.Navigate
import com.company.test_app.data.entity.network.Result
import com.company.test_app.data.entity.presentation.Currency
import com.company.test_app.data.entity.presentation.Department
import com.company.test_app.data.entity.presentation.PrivatBank
import com.company.test_app.data.entity.presentation.ResponseTso
import com.company.test_app.domain.interactor.PrivatBankInteractor
import com.company.test_app.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class PrivatBankViewModel @Inject constructor(private var privatBankInteractor: PrivatBankInteractor) : BaseViewModel() {

    var getCurrencyLiveData : LiveData<Result<PrivatBank>>
    private var _getCurrencyLiveData = MutableLiveData<Result<PrivatBank>>()

    var getDepartmentLiveData : LiveData<Result<List<Department>>>
    private var _getDepartmentLiveData = MutableLiveData<Result<List<Department>>>()

    var getTsoLiveData : LiveData<Result<ResponseTso>>
    private var _getTsoLiveData = MutableLiveData<Result<ResponseTso>>()

    var currentDate: String = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())

    init {
        getCurrencyLiveData = _getCurrencyLiveData
        getDepartmentLiveData = _getDepartmentLiveData
        getTsoLiveData = _getTsoLiveData
    }

    fun getExchangePrivatBank(date :String) {
        viewModelScope.launch (Dispatchers.IO) {
            privatBankInteractor.getExchange( date, { privatBnk->
                val arrayCurrency: ArrayList<Currency> = ArrayList<Currency>()
                val iterator: ListIterator<Currency> = privatBnk.exchangeRate.listIterator()
                while (iterator.hasNext()) {
                    val next: Currency = iterator.next()
                    if (next.currency == null) {
                        continue
                    }
                    if (next.currency == "USD" || next.currency == "EUR" || next.currency == "RUB")
                        arrayCurrency.add(next)
                }
                val privatBankChange  = PrivatBank(
                    privatBnk.date,
                    privatBnk.bank,
                    privatBnk.baseCurrency,
                    privatBnk.baseCurrencyLit,
                    arrayCurrency)
                _getCurrencyLiveData.postValue(Result.Success(privatBankChange))
            }, {
                _getCurrencyLiveData.postValue(Result.Error(it))
            })
        }
    }

    fun getDepartmentPrivatBank() {
        viewModelScope.launch (Dispatchers.IO) {
            privatBankInteractor.getDepartment({
                _getDepartmentLiveData.postValue(Result.Success(it))
            }, {
                _getDepartmentLiveData.postValue(Result.Error(it))
            })
        }
    }

    fun getTsoPrivatBank() {
        viewModelScope.launch (Dispatchers.IO) {
            privatBankInteractor.getTso({
                _getTsoLiveData.postValue(Result.Success(it))
            }, {
                _getTsoLiveData.postValue(Result.Error(it))
            })
        }
    }

    fun goToDepartment() {
        navigateTo(Navigate.Global.Department)
    }

    fun goToTso() {
        navigateTo(Navigate.Global.TSO)
    }

    fun main() = runBlocking<Unit> {
        try {
            failedConcurrentSum()
        } catch(e: ArithmeticException) {
            println("Computation failed with ArithmeticException")
        }
    }

    suspend fun failedConcurrentSum(): Int = coroutineScope {
        val one = async<Int> {
            try {
                delay(Long.MAX_VALUE) // Emulates very long computation
                42
            } finally {
                println("First child was cancelled")
            }
        }
        val two = async<Int> {
            println("Second child throws an exception")
            throw ArithmeticException()
        }
        one.await() + two.await()
    }
}