package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.presentation.workers.ChildWorkerFactory
import com.turtle.amatda.presentation.workers.SeedDatabaseWorker
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