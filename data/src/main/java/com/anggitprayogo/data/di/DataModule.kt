package com.anggitprayogo.data.di

import com.anggitprayogo.data.remote.routes.NetworkService
import com.anggitprayogo.network.Network.retrofitClient
import dagger.Module
import dagger.Provides

/**
 * Created by Anggit Prayogo on 6/24/20.
 */
@Module
class DataModule {

    @Provides
    @DataScope
    fun provideService(): NetworkService {
        return retrofitClient().create(NetworkService::class.java)
    }
}