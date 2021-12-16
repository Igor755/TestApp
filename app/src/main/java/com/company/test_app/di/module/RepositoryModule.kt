package com.company.test_app.di.module

import com.company.test_app.data.repository.ItemsRepositoryImpl
import com.company.test_app.data.repository.PrivatBankRepositoryImpl
import com.company.test_app.data.repository.NetworkStateRepositoryImpl
import com.company.test_app.data.repository.ProgressRepositoryImpl
import com.company.test_app.domain.repository.IItemsRepository
import com.company.test_app.domain.repository.INetworkStateRepository
import com.company.test_app.domain.repository.IProgressRepository
import com.company.test_app.domain.repository.PrivatBankRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsItemsRepository(repository: ItemsRepositoryImpl): IItemsRepository

    @Binds
    @Singleton
    abstract fun bindsProgressRepository(repository: ProgressRepositoryImpl): IProgressRepository

    @Binds
    @Singleton
    abstract fun bindsNetworkStateRepository(repository: NetworkStateRepositoryImpl): INetworkStateRepository

    @Binds
    @Singleton
    abstract fun bindsMatchRepository(repository: PrivatBankRepositoryImpl): PrivatBankRepository
}