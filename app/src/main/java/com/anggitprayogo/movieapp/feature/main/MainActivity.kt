package com.anggitprayogo.movieapp.feature.main

import android.os.Bundle
import com.anggitprayogo.core.base.BaseActivity
import com.anggitprayogo.movieapp.BaseApplication
import com.anggitprayogo.movieapp.R
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }
}