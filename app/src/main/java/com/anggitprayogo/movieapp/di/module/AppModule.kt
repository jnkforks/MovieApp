package com.anggitprayogo.movieapp.di.module

import com.anggitprayogo.core.util.thread.AppSchedulerProvider
import com.anggitprayogo.core.util.thread.SchedulerProvider
import com.anggitprayogo.movieapp.data.local.dao.MovieDao
import com.anggitprayogo.movieapp.data.remote.routes.NetworkService
import com.anggitprayogo.movieapp.data.repository.MovieRepository
import com.anggitprayogo.movieapp.data.repository.MovieRepositoryImpl
import com.anggitprayogo.movieapp.domain.MovieUseCase
import com.anggitprayogo.network.Network.retrofitClient
import dagger.Module
import dagger.Provides

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Module(includes = [RoomModule::class])
class AppModule {

    @Provides
    fun provideNetworkService() : NetworkService{
        return retrofitClient().create(NetworkService::class.java)
    }

    @Provides
    fun provideRepository(service: NetworkService, movieDao: MovieDao): MovieRepository {
        return MovieRepositoryImpl(service, movieDao)
    }

    @Provides
    fun provideUseCase(repository: MovieRepository): MovieUseCase {
        return MovieUseCase(repository)
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}