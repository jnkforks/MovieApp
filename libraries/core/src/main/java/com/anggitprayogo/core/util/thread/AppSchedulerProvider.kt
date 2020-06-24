package com.anggitprayogo.core.util.thread

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppSchedulerProvider : SchedulerProvider {
    override fun ui(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}