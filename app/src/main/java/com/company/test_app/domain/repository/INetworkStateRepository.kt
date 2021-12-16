package com.company.test_app.domain.repository

import androidx.lifecycle.LiveData
import com.company.test_app.common.event.NetworkStateEvent

interface INetworkStateRepository {

    fun showNetworkConnectionError()
    fun showNetworkConnectionErrorDialog()
    fun hideNetworkConnectionError()
    fun observeNetworkConnectionState(): LiveData<NetworkStateEvent>
}