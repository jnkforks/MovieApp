package com.anggitprayogo.core.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
abstract class BaseActivity : AppCompatActivity(){

    abstract fun initInjector()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        initInjector()
        super.onCreate(savedInstanceState, persistentState)
    }
}