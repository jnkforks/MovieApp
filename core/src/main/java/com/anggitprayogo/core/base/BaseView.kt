package com.anggitprayogo.core.base

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface BaseView {
    fun onMessage(message: String?)
    fun onMessage(stringResId: Int)
    fun isNetworkConnect(): Boolean
    fun hideKeyboard()
}