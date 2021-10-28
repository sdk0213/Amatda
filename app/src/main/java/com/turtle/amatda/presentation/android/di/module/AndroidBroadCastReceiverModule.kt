package com.turtle.amatda.presentation.android.di.module

import com.turtle.amatda.presentation.android.broadcast.BootUpBroadCastReceiver
import com.turtle.amatda.presentation.android.di.scope.BroadCastReceiverScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidBroadCastReceiverModule {

    @BroadCastReceiverScope
    @ContributesAndroidInjector(modules = [BroadcastReceiverModule::class])
    abstract fun getBootUpBroadCastReceiver(): BootUpBroadCastReceiver
}