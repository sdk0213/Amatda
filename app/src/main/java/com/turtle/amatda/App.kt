package com.turtle.amatda

import com.turtle.amatda.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?> {
        return DaggerAppComponent.factory().create(this)
    }
}