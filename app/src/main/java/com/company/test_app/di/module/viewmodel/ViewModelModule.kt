package com.company.test_app.di.module.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.company.test_app.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module(includes = [ActivityViewModelModule::class, FragmentViewModelModule::class])
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}