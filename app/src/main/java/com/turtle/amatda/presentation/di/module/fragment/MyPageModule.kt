package com.turtle.amatda.presentation.di.module.fragment

import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentMypageBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class MyPageModule {

    @Provides
    @FragmentScope
    fun provideFragmentMyPageBinding(activity: MainActivity): FragmentMypageBinding {
        return DataBindingUtil.inflate<FragmentMypageBinding>(
            activity.layoutInflater,
            R.layout.fragment_mypage,
            null,
            false
        )
    }

}