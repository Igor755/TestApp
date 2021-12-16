package com.company.test_app.presentation.viewmodel

import com.company.test_app.common.SingleLiveData
import com.company.test_app.common.event.StateEvent
import com.company.test_app.domain.usercase.BasePageCoroutinesUseCase
import com.company.test_app.domain.usercase.network.ObserveNetworkStateUserCase


abstract class BasePageViewModel<T>(
    observeNetworkStateUserCase: ObserveNetworkStateUserCase? = null
) : BaseViewModel(observeNetworkStateUserCase) {

    var isLastPage: Boolean = true
    var isLoading: Boolean = false

    val loadMoreItems: SingleLiveData<List<T>> = SingleLiveData()
    val initItems: SingleLiveData<List<T>> = SingleLiveData()
    val emptyStateEvent: SingleLiveData<StateEvent> = SingleLiveData()

    protected open fun loadFirstPage(userCase: BasePageCoroutinesUseCase<List<T>>) {
        loadNextPage(userCase, isFirstPage = true)
    }

    protected fun loadNextPage(
        userCase: BasePageCoroutinesUseCase<List<T>>,
        isFirstPage: Boolean = false
    ) {
        if (!isFirstPage) userCase.increasePage() else userCase.resetPage()
        isLoading = true
        userCase.execute {
            onComplete {
                isLastPage = it.size < userCase.getPerPage()
                when (userCase.page) {
                    1L -> initItems.value = it
                    else -> loadMoreItems.value = it
                }
                emptyStateEvent.value = if (it.isEmpty()) StateEvent.SHOW else StateEvent.HIDE
                isLoading = false
            }
            onNetworkError { isLoading = false }
            onError { isLoading = false }
            onCancel { isLoading = false }
        }
    }

    protected fun loadPrevPage(userCase: BasePageCoroutinesUseCase<List<T>>) {
        isLoading = true
        userCase.decreasePage()
        userCase.execute {
            onComplete {
                when (userCase.page) {
                    1L -> initItems.value = it
                    else -> loadMoreItems.value = it
                }
                emptyStateEvent.value = if (it.isEmpty()) StateEvent.SHOW else StateEvent.HIDE
                isLoading = false
                isLastPage = false
            }
            onNetworkError { isLoading = false }
            onError { isLoading = false }
            onCancel { isLoading = false }
        }
    }
}