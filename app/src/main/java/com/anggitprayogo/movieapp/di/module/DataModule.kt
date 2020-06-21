package com.anggitprayogo.movieapp.di.module

import com.anggitprayogo.movieapp.data.routes.NetworkService
import com.anggitprayogo.movieapp.di.DataScope
import com.anggitprayogo.movieapp.network.Network
import dagger.Module
import dagger.Provides

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Module
class DataModule {

    @Provides
    @DataScope
    fun provideNetworkService(): NetworkService {
        return Network.retrofitClient().create(NetworkService::class.java)
    }
}