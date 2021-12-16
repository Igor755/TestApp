package com.company.test_app

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import com.company.test_app.di.ApplicationComponent
import com.company.test_app.di.DaggerApplicationComponent
import com.company.test_app.domain.repository.INetworkStateRepository
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber
import javax.inject.Inject

class App: DaggerApplication() {

    companion object {
        lateinit var appComponent: ApplicationComponent
    }

    @Inject
    lateinit var networkStateRepository: INetworkStateRepository

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
        return appComponent
    }

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            cm?.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    networkStateRepository.hideNetworkConnectionError()
                }

                override fun onLost(network: Network) {
                    networkStateRepository.showNetworkConnectionError()
                }
            })
        }

    }
}