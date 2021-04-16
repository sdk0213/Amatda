package com.turtle.amatda.ui.setting

import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentSettingBinding
import com.turtle.amatda.databinding.FragmentTodoBinding
import com.turtle.amatda.di.qualifier.ApplicationContext
import com.turtle.amatda.di.scope.FragmentScope
import com.turtle.amatda.ui.MainActivity
import dagger.Module
import dagger.Provides

@Module
class SettingModule {

    @Provides
    @FragmentScope
    fun provideFragmentSettingBinding(activity: MainActivity): FragmentSettingBinding {
        return DataBindingUtil.inflate<FragmentSettingBinding>(
            activity.layoutInflater,
            R.layout.fragment_setting,
            null,
            false
        )
    }


    @Provides
    @FragmentScope
    fun provideGridLayoutManager(@ApplicationContext context: Context): GridLayoutManager {
        return GridLayoutManager(context, 3)
    }

}