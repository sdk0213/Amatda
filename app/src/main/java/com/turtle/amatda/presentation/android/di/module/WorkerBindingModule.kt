package com.turtle.amatda.presentation.android.di.module

import com.turtle.amatda.presentation.android.di.factory.ChildWorkerFactory
import com.turtle.amatda.presentation.android.di.key.WorkerKey
import com.turtle.amatda.presentation.android.workmanager.KeepServiceWorker
import com.turtle.amatda.presentation.android.workmanager.SeedDatabaseWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerBindingModule {

    @Binds
    @IntoMap
    @WorkerKey(SeedDatabaseWorker::class)
    fun bindSeedDatabaseWorker(factory: SeedDatabaseWorker.Factory): ChildWorkerFactory

    @Binds
    @IntoMap
    @WorkerKey(KeepServiceWorker::class)
    fun bindKeepServiceWorker(factory: KeepServiceWorker.Factory): ChildWorkerFactory
}