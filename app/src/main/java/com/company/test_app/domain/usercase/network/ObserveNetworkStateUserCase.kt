package com.company.test_app.domain.usercase.network

import androidx.lifecycle.LiveData
import com.company.test_app.common.event.NetworkStateEvent
import com.company.test_app.domain.repository.INetworkStateRepository
import com.company.test_app.domain.usercase.BaseObserveUseCase
import javax.inject.Inject

class ObserveNetworkStateUserCase @Inject constructor(
    private val networkStateRepository: INetworkStateRepository
) : BaseObserveUseCase<NetworkStateEvent>() {

    override fun observe(): LiveData<NetworkStateEvent> =
        networkStateRepository.observeNetworkConnectionState()
}