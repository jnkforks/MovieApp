package com.anggitprayogo.movieapp.feature.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log.e
import androidx.constraintlayout.motion.widget.MotionLayout
import com.anggitprayogo.core.base.BaseActivity
import com.anggitprayogo.core.util.ext.hideSystemUIAndNavigation
import com.anggitprayogo.movieapp.BaseApplication
import com.anggitprayogo.movieapp.R
import com.anggitprayogo.movieapp.feature.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        initInjector()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        listenMotionlayout()
    }

    private fun listenMotionlayout() {
        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                p0?.let {
                    if (it.currentState != -1) {
                        val intent =
                            Intent(this@SplashScreenActivity, MainActivity::class.java).apply {
                                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            }
                        startActivity(intent)
                    }
                }
                e("TRANSITION: ", "COMPLETED ${p0?.currentState}")
            }

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
                e("TRANSITION: ", "CHANGED ${p0?.currentState}")
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
                e("TRANSITION: ", "STARTED ${p0?.currentState}")
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
                e("TRANSITION: ", "STARTED ${p0?.currentState}")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        motionLayout.startLayoutAnimation()
    }

    override fun initInjector() {
        (application as BaseApplication).appComponent.inject(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUIAndNavigation()
    }
}