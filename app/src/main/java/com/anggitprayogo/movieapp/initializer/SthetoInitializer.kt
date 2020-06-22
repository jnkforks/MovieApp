package com.anggitprayogo.movieapp.initializer

import android.content.Context
import androidx.startup.Initializer
import com.facebook.stetho.Stetho

/**
 * Created by Anggit Prayogo on 6/22/20.
 */
class SthetoInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Stetho.initializeWithDefaults(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}