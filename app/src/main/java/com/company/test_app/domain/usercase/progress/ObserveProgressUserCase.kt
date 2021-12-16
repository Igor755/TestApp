package com.company.test_app.domain.usercase.progress

import androidx.lifecycle.LiveData
import com.company.test_app.common.event.StateEvent
import com.company.test_app.domain.repository.IProgressRepository
import com.company.test_app.domain.usercase.BaseObserveUseCase
import javax.inject.Inject

class ObserveProgressUserCase @Inject constructor(
    private val progressRepository: IProgressRepository
) : BaseObserveUseCase<StateEvent>() {

    override fun observe(): LiveData<StateEvent> = progressRepository.observeProgressState()
}