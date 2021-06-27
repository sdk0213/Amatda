package com.turtle.amatda.presentation.di.module

import android.app.Application
import android.content.Context
import com.turtle.amatda.presentation.view.main.App
import com.turtle.amatda.presentation.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import timber.log.Timber
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    @Singleton
    fun provideApp(app: App): Application = app

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(app: App): Context = app

}