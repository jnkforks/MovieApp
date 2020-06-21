package com.anggitprayogo.core.util.state

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
sealed class LoaderState {
    object ShowLoading: LoaderState()
    object HideLoading: LoaderState()
}