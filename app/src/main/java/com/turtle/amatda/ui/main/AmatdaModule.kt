package com.turtle.amatda.ui.main

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentAmatdaBinding
import com.turtle.amatda.di.scope.FragmentScope
import com.turtle.amatda.ui.MainActivity
import dagger.Module
import dagger.Provides

@Module
class AmatdaModule {

    //데이터 바인딩 클래스 제공
    @Provides
    @FragmentScope
    fun provideFragmentAmatdaBinding(activity: MainActivity): FragmentAmatdaBinding {
        return DataBindingUtil.inflate<FragmentAmatdaBinding>(activity.layoutInflater, R.layout.fragment_amatda, null, false)
    }

    @Provides
    @FragmentScope
    fun provideFragment(fragment: AmatdaFragment): Fragment {
        return fragment
    }

}