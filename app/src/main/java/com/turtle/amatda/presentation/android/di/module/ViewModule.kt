package com.turtle.amatda.presentation.android.di.module

import android.content.Context
import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.ActivityMainBinding
import com.turtle.amatda.presentation.android.di.qualifier.ActivityContext
import com.turtle.amatda.presentation.android.di.scope.ActivityScope
import com.turtle.amatda.presentation.android.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.carrier.CarrierFragment
import com.turtle.amatda.presentation.view.carrier_item.CarrierItemFragment
import com.turtle.amatda.presentation.view.carrier_name.CarrierNameFragment
import com.turtle.amatda.presentation.view.carrier_size.CarrierSizeFragment
import com.turtle.amatda.presentation.view.carrier_type.CarrierTypeFragment
import com.turtle.amatda.presentation.view.home.HomeFragment
import com.turtle.amatda.presentation.view.intro.IntroFragment
import com.turtle.amatda.presentation.view.login.LoginFragment
import com.turtle.amatda.presentation.view.main.MainActivity
import com.turtle.amatda.presentation.view.main.MainViewPagerFragment
import com.turtle.amatda.presentation.view.mypage.MyPageFragment
import com.turtle.amatda.presentation.view.trip.TripFragment
import com.turtle.amatda.presentation.view.trip_concept.TripConceptFragment
import com.turtle.amatda.presentation.view.trip_date.TripDateFragment
import com.turtle.amatda.presentation.view.trip_title.TripTitleFragment
import com.turtle.amatda.presentation.view.trip_zone.TripZoneFragment
import com.turtle.amatda.presentation.view.trip_zone_make.TripZoneMakeFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewModule {

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
    @ContributesAndroidInjector(modules = [IntroModule::class])
    abstract fun getIntroFragment(): IntroFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun getLoginFragment(): LoginFragment

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
    @ContributesAndroidInjector(modules = [CarrierNameModule::class])
    abstract fun getCarrierNameFragment(): CarrierNameFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [CarrierItemModule::class])
    abstract fun getCarrierItemFragment(): CarrierItemFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TripModule::class])
    abstract fun getSettingFragment(): TripFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MyPageModule::class])
    abstract fun getMyPageFragment(): MyPageFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TripTitleModule::class])
    abstract fun getTripTitleFragment(): TripTitleFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TripDateModule::class])
    abstract fun getTripDateFragment(): TripDateFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TripConceptModule::class])
    abstract fun getTripConceptFragment(): TripConceptFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TripZoneModule::class])
    abstract fun getTripZoneFragment(): TripZoneFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [TripZoneMakeModule::class])
    abstract fun getTripZoneMakeFragment(): TripZoneMakeFragment
}