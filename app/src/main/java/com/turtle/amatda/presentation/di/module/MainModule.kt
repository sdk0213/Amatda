package com.turtle.amatda.presentation.di.module

import android.content.Context
import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.ActivityMainBinding
import com.turtle.amatda.presentation.di.module.fragment.*
import com.turtle.amatda.presentation.view.main.MainActivity
import com.turtle.amatda.presentation.di.qualifier.ActivityContext
import com.turtle.amatda.presentation.di.scope.ActivityScope
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.date.DateFragment
import com.turtle.amatda.presentation.view.home.HomeViewPagerFragment
import com.turtle.amatda.presentation.view.itemselect.ItemSelectFragment
import com.turtle.amatda.presentation.view.setting.SettingFragment
import com.turtle.amatda.presentation.view.todo.TodoFragment
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
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun getAmatadaFragment(): HomeViewPagerFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TodoModule::class])
    abstract fun getTodoFragment(): TodoFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [SettingModule::class])
    abstract fun getSettingFragment(): SettingFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [DateModule::class])
    abstract fun getDateFragment(): DateFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ItemSelectModule::class])
    abstract fun getItemSelectFragment(): ItemSelectFragment
}