package com.company.test_app.di.module.viewmodel

import androidx.lifecycle.ViewModel
import com.company.test_app.di.mapkey.ViewModelKey
import com.company.test_app.presentation.viewmodel.main.PrivatBankViewModel
import com.company.test_app.presentation.viewmodel.main.SecondViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PrivatBankViewModel::class)
    abstract fun bindFirstViewModel(viewModel: PrivatBankViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SecondViewModel::class)
    abstract fun bindSecondViewModel(viewModel: SecondViewModel): ViewModel
}