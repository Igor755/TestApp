package com.company.test_app.presentation.viewmodel

import com.company.test_app.domain.usercase.BasePageCoroutinesUseCase
import com.company.test_app.domain.usercase.BaseQueryPageCoroutinesUseCase
import com.company.test_app.domain.usercase.network.ObserveNetworkStateUserCase


abstract class BaseQueryPageViewModel<T>(
    observeNetworkStateUserCase: ObserveNetworkStateUserCase? = null
) : BasePageViewModel<T>(observeNetworkStateUserCase) {

    companion object {
        const val MIN_CHARS = 3
    }

    protected var query: String? = null

    protected fun loadQueryFirstPage(
        query: String,
        useCase: BaseQueryPageCoroutinesUseCase<List<T>>
    ) {
        this.query = query
        useCase.setDelay(500L)
        useCase.setData(query)
        super.loadFirstPage(useCase)
    }

    override fun loadFirstPage(userCase: BasePageCoroutinesUseCase<List<T>>) {
        query ?: let { super.loadFirstPage(userCase) }
    }
}