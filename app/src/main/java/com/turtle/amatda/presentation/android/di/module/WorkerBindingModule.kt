package com.turtle.amatda.presentation.android.di.module

import com.turtle.amatda.presentation.android.workers.ChildWorkerFactory
import com.turtle.amatda.presentation.android.workers.SeedDatabaseWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(SeedDatabaseWorker::class)
    fun bindSeedDatabaseWorker(factory: SeedDatabaseWorker.Factory): ChildWorkerFactory
}