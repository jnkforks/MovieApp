package com.anggitprayogo.movieapp

import android.app.Application
import androidx.startup.AppInitializer
import com.anggitprayogo.core.config.FontConfig
import com.anggitprayogo.movieapp.di.AppComponent
import com.anggitprayogo.movieapp.di.DaggerAppComponent
import com.anggitprayogo.movieapp.initializer.SthetoInitializer
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class BaseApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        AppInitializer.getInstance(this)
            .initializeComponent(SthetoInitializer::class.java)
        initFont()
    }

    private fun initFont() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath(FontConfig.FONT_REGULAR)
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }
}
