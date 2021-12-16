package com.company.test_app.di.module.viewmodel

import androidx.lifecycle.ViewModel
import com.company.test_app.di.mapkey.ViewModelKey
import com.company.test_app.presentation.viewmodel.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ActivityViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}