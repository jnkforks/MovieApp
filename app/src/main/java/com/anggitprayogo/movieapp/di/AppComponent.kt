package com.anggitprayogo.movieapp.di

import android.content.Context
import com.anggitprayogo.movieapp.di.module.AppModule
import com.anggitprayogo.movieapp.di.module.DataModule
import com.anggitprayogo.movieapp.di.module.RoomModule
import com.anggitprayogo.movieapp.di.module.ViewModelModule
import com.anggitprayogo.movieapp.feature.detail.MovieDetailActivity
import com.anggitprayogo.movieapp.feature.favouritedetail.FavouriteDetailActivity
import com.anggitprayogo.movieapp.feature.favouritelist.FavouriteListActivity
import com.anggitprayogo.movieapp.feature.main.MainActivity
import com.anggitprayogo.movieapp.feature.splash.SplashScreenActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
@Singleton
@Component(
    modules = [DataModule::class, ViewModelModule::class, AppModule::class, RoomModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(activity: MovieDetailActivity)
    fun inject(activity: FavouriteListActivity)
    fun inject(activity: FavouriteDetailActivity)
    fun inject(activity: SplashScreenActivity)
}