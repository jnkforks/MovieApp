package com.anggitprayogo.core.base

import androidx.lifecycle.ViewModel
import com.anggitprayogo.core.util.thread.SchedulerProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.isActive
import kotlin.coroutines.CoroutineContext

/**
 * Created by Anggit Prayogo on 6/21/20.
 */
abstract class BaseViewModel(private val baseDispatcher: SchedulerProvider) : ViewModel(),
    CoroutineScope {

    private val supervisorJob = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = baseDispatcher.ui() + supervisorJob

    open fun clear() {
        if (isActive && !supervisorJob.isCancelled) {
            supervisorJob.children.map {
                it.cancel()
            }
        }
    }

}