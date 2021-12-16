package com.company.test_app.data.network

import android.content.Context
import android.content.Intent
import com.company.test_app.common.BaseBroadcastReceiver
import com.company.test_app.common.NetworkUtils
import com.company.test_app.domain.repository.INetworkStateRepository
import javax.inject.Inject

class NetworkChangeBroadcastReceiver : BaseBroadcastReceiver() {

    @Inject
    lateinit var networkStateRepository: INetworkStateRepository

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        takeIf { NetworkUtils.isOnline() }?.let {
            networkStateRepository.hideNetworkConnectionError()
        } ?: let { networkStateRepository.showNetworkConnectionError() }
    }

}