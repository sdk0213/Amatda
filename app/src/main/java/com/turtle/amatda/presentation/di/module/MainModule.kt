package com.turtle.amatda.presentation.di.module

import android.content.Context
import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.ActivityMainBinding
import com.turtle.amatda.presentation.di.module.fragment.*
import com.turtle.amatda.presentation.di.qualifier.ActivityContext
import com.turtle.amatda.presentation.di.scope.ActivityScope
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.carrier.CarrierFragment
import com.turtle.amatda.presentation.view.carrier_size.CarrierSizeFragment
import com.turtle.amatda.presentation.view.carrier_type.CarrierTypeFragment
import com.turtle.amatda.presentation.view.home.HomeFragment
import com.turtle.amatda.presentation.view.main.MainActivity
import com.turtle.amatda.presentation.view.main.MainViewPagerFragment
import com.turtle.amatda.presentation.view.mypage.MyPageFragment
import com.turtle.amatda.presentation.view.trip.TripFragment
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
    @ContributesAndroidInjector(modules = [MainViewPagerModule::class])
    abstract fun getMainViewPagerFragment(): MainViewPagerFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun getHomeFragment(): HomeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CarrierModule::class])
    abstract fun getCarrierFragment(): CarrierFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CarrierTypeModule::class])
    abstract fun getCarrierTypeFragment(): CarrierTypeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CarrierSizeModule::class])
    abstract fun getCarrierSizeFragment(): CarrierSizeFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TripModule::class])
    abstract fun getSettingFragment(): TripFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MyPageModule::class])
    abstract fun getMyPageFragment(): MyPageFragment
}