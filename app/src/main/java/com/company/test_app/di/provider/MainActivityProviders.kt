package com.company.test_app.di.provider

import com.company.test_app.presentation.ui.main.fragment.TsoFragment
import com.company.test_app.presentation.ui.main.fragment.PrivatBankFragment
import com.company.test_app.presentation.ui.main.fragment.DepartmentFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityProviders {

    @ContributesAndroidInjector
    abstract fun providePrivatBankFragment(): PrivatBankFragment

    @ContributesAndroidInjector
    abstract fun provideDepartmentFragment(): DepartmentFragment

    @ContributesAndroidInjector
    abstract fun provideAtmFragment(): TsoFragment
}