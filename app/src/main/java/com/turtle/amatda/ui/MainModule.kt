package com.turtle.amatda.ui

import android.content.Context
import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.ActivityMainBinding
import com.turtle.amatda.di.qualifier.ActivityContext
import com.turtle.amatda.di.scope.ActivityScope
import com.turtle.amatda.di.scope.FragmentScope
import com.turtle.amatda.ui.setting.SettingFragment
import com.turtle.amatda.ui.setting.SettingModule
import com.turtle.amatda.ui.todo.TodoFragment
import com.turtle.amatda.ui.todo.TodoModule
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainModule {

    companion object {

        @Provides
        @ActivityScope
        fun provideBinding(activity: MainActivity): ActivityMainBinding {
            return DataBindingUtil.setContentView(activity, R.layout.activity_main)
        }

        @Provides
        @ActivityContext
        fun provideContext(activity: MainActivity): Context {
            return activity
        }

    }

    @FragmentScope
    @ContributesAndroidInjector(modules = [TodoModule::class])
    abstract fun getTodoFragment(): TodoFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SettingModule::class])
    abstract fun getSettingFragment(): SettingFragment
}