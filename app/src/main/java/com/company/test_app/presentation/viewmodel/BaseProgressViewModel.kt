package com.company.test_app.presentation.viewmodel

import androidx.lifecycle.Observer
import com.company.test_app.common.SingleLiveData
import com.company.test_app.common.event.StateEvent
import com.company.test_app.domain.usercase.network.ObserveNetworkStateUserCase
import com.company.test_app.domain.usercase.progress.ObserveProgressUserCase

abstract class BaseProgressViewModel(
    observeProgressUserCase: ObserveProgressUserCase,
    observeNetworkStateUserCase: ObserveNetworkStateUserCase? = null
) : BaseViewModel(observeNetworkStateUserCase) {

    val progressGlobalEvent = SingleLiveData<StateEvent>()

    private val progressObserve = Observer<StateEvent> {
        progressGlobalEvent.value = it
    }

    init {
        observeProgressUserCase.apply { observe().observeForever(progressObserve) }
    }
}