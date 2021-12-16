package com.company.test_app.presentation.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.company.test_app.common.Navigate
import com.company.test_app.common.SingleLiveData
import com.company.test_app.common.event.NetworkStateEvent
import com.company.test_app.domain.usercase.network.ObserveNetworkStateUserCase

abstract class BaseViewModel(
    observeNetworkStateUserCase: ObserveNetworkStateUserCase? = null
) : ViewModel() {

    val navigationGlobalEvent = SingleLiveData<Navigate.Global>()
    val networkConnectionGlobalEvent = SingleLiveData<NetworkStateEvent>()

    private val networkConnectionObserve = Observer<NetworkStateEvent> {
        networkConnectionGlobalEvent.value = it
    }

    protected fun navigateTo(event: Navigate.Global) {
        navigationGlobalEvent.value = event
    }

    protected fun navigateUp() {
        navigationGlobalEvent.value = Navigate.Global.Back
    }

    open fun onResume() {
        //do nothing
    }

    open fun onPause() {

    }
}