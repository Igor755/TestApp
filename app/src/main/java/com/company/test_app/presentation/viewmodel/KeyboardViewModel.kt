package com.company.test_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.company.test_app.common.SingleLiveData
import com.company.test_app.common.event.StateEvent
import javax.inject.Inject

class KeyboardViewModel @Inject constructor() : ViewModel() {

    val keyboardEvent: SingleLiveData<StateEvent> = SingleLiveData()

    fun showKeyboard() {
        keyboardEvent.value = StateEvent.SHOW
    }

    fun hideKeyboard() {
        keyboardEvent.value = StateEvent.HIDE
    }
}