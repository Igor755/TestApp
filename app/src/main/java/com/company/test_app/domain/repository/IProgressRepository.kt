package com.company.test_app.domain.repository

import androidx.lifecycle.LiveData
import com.company.test_app.common.event.StateEvent

interface IProgressRepository {

    fun showProgressBar(obj: Any)
    fun hideProgressBar(obj: Any)
    fun observeProgressState(): LiveData<StateEvent>
}