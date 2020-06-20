package com.anggitprayogo.core.util.thread

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
class TestSchedulerProvider : SchedulerProvider {
    override fun ui(): CoroutineDispatcher = Dispatchers.Unconfined
}