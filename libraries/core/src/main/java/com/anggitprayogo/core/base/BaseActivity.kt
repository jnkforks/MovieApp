package com.anggitprayogo.core.base

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
abstract class BaseActivity : AppCompatActivity() {

    abstract fun initInjector()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        initInjector()
        super.onCreate(savedInstanceState, persistentState)
    }
}