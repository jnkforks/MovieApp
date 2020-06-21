package com.anggitprayogo.movieapp

import android.app.Application
import com.anggitprayogo.movieapp.di.AppComponent
import com.anggitprayogo.movieapp.di.DaggerAppComponent

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class BaseApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}