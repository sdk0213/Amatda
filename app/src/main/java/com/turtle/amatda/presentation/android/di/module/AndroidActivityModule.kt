package com.turtle.amatda.presentation.android.di.module

import com.turtle.amatda.presentation.android.di.scope.ActivityScope
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AndroidActivityModule {
    /**
     * MainActivity를 위한 서브컴포넌트 정의한다.
     */
    @ActivityScope
    @ContributesAndroidInjector(modules = [ViewModule::class])
    abstract fun mainActivity(): MainActivity
}