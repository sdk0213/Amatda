package com.turtle.amatda.ui.setting

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.turtle.amatda.databinding.FragmentSettingBinding
import com.turtle.amatda.di.qualifier.ApplicationContext
import com.turtle.amatda.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class SettingModule {

    @Provides
    @FragmentScope
    fun provideFragmentSettingBinding(@ApplicationContext context: Context): FragmentSettingBinding {
        return FragmentSettingBinding.inflate(LayoutInflater.from(context), null, false)
    }


    @Provides
    @FragmentScope
    fun provideGridLayoutManager(@ApplicationContext context: Context): GridLayoutManager {
        return GridLayoutManager(context, 3)
    }

}