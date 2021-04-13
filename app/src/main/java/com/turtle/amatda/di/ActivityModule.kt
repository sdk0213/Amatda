package com.turtle.amatda.di

import com.turtle.amatda.di.scope.ActivityScope
import com.turtle.amatda.ui.MainActivity
import com.turtle.amatda.ui.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityModule {
    /**
     * MainActivity를 위한 서브컴포넌트 정의한다.
     */
    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun mainActivity(): MainActivity
}