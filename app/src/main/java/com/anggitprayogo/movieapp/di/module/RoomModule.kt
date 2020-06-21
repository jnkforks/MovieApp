package com.anggitprayogo.movieapp.di.module

import android.content.Context
import androidx.room.Room
import com.anggitprayogo.movieapp.data.db.AppDatabase
import com.anggitprayogo.movieapp.data.db.dao.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Module
class RoomModule {
    private val mDataBaseName = "MovieApp.db"

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            mDataBaseName
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}