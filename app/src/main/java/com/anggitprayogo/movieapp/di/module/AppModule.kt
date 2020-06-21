package com.anggitprayogo.movieapp.di.module

import com.anggitprayogo.core.util.thread.AppSchedulerProvider
import com.anggitprayogo.core.util.thread.SchedulerProvider
import com.anggitprayogo.movieapp.data.db.dao.MovieDao
import com.anggitprayogo.movieapp.data.repository.MovieRepository
import com.anggitprayogo.movieapp.data.repository.MovieRepositoryImpl
import com.anggitprayogo.movieapp.data.routes.NetworkService
import com.anggitprayogo.movieapp.di.DataScope
import com.anggitprayogo.movieapp.domain.MovieUseCase
import dagger.Module
import dagger.Provides

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Module(includes = [DataModule::class, RoomModule::class])
class AppModule {

    @Provides
    fun provideRepository(@DataScope service: NetworkService, movieDao: MovieDao): MovieRepository {
        return MovieRepositoryImpl(service, movieDao)
    }

    @Provides
    fun provideUseCase(repository: MovieRepository): MovieUseCase {
        return MovieUseCase(repository)
    }

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}