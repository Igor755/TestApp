package com.company.test_app.presentation.viewmodel.main

import com.company.test_app.common.Navigate
import com.company.test_app.presentation.viewmodel.BaseViewModel
import javax.inject.Inject

class SecondViewModel @Inject constructor(): BaseViewModel() {

    fun onCloseClicked(){
        navigateTo(Navigate.Global.Close)
    }

}