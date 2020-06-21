package com.anggitprayogo.core.util.ext

import android.app.Activity
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