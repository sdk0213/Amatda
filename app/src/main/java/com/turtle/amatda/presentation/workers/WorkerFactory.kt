package com.turtle.amatda.presentation.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class WorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        params: WorkerParameters
    ): ListenableWorker? {
        val foundEntry = workerFactories.entries
            .find { Class.forName(workerClassName).isAssignableFrom(it.key) }
            ?: return null
        return foundEntry.value.get().create(appContext, params)
    }
}