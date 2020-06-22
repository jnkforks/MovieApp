package com.anggitprayogo.core.util.ext

import android.app.Activity
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
fun Activity.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Activity.hideSystemUIAndNavigation() {
    val decorView: View = window.decorView
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_IMMERSIVE
                    // Set the content to appear under the system bars so that the
                    // content doesn't resize when the system bars hide and show.
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN // Hide the nav bar and status bar
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }
}