package com.turtle.amatda.presentation.di.module

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

@Module(includes = [AssistedInject_AssistedWorkerInjectModule::class])
@AssistedModule
interface AssistedWorkerInjectModule