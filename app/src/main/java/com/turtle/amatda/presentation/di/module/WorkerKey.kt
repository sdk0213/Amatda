package com.turtle.amatda.presentation.di.module

import androidx.work.ListenableWorker
import com.turtle.amatda.presentation.workers.SeedDatabaseWorker
import com.squareup.inject.assisted.dagger2.AssistedModule
import com.turtle.amatda.presentation.workers.ChildWorkerFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)