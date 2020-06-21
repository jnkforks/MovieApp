package com.anggitprayogo.movieapp.di

import android.content.Context
import com.anggitprayogo.movieapp.di.module.AppModule
import com.anggitprayogo.movieapp.di.module.DataModule
import com.anggitprayogo.movieapp.di.module.ViewModelModule
import com.anggitprayogo.movieapp.feature.detail.MovieDetailActivity
import com.anggitprayogo.movieapp.feature.main.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Singleton
@Component(
    modules = [DataModule::class, ViewModelModule::class, AppModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: MovieDetailActivity)
}