package com.anggitprayogo.movieapp.feature.splash

import android.os.Bundle
import com.anggitprayogo.core.base.BaseActivity
import com.anggitprayogo.core.util.ext.hideSystemUIAndNavigation
import com.anggitprayogo.movieapp.BaseApplication
import com.anggitprayogo.movieapp.R

class SplashScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUIAndNavigation()
    }
}