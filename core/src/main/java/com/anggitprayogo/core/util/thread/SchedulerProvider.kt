package com.anggitprayogo.core.util.thread

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
interface SchedulerProvider {
    fun ui(): CoroutineDispatcher
}