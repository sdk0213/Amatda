package com.turtle.amatda.presentation.di.module

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentItemselectBinding
import com.turtle.amatda.presentation.di.qualifier.ApplicationContext
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class ItemSelectModule {

    @Provides
    @FragmentScope
    fun provideFragmentItemselectBinding(activity: MainActivity): FragmentItemselectBinding {
        return DataBindingUtil.inflate<FragmentItemselectBinding>(
            activity.layoutInflater,
            R.layout.fragment_itemselect,
            null,
            false
        )
    }


}