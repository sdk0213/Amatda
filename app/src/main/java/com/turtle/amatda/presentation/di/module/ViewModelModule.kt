package com.turtle.amatda.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turtle.amatda.presentation.di.AppViewModelFactory
import com.turtle.amatda.presentation.view.date.DateViewModel
import com.turtle.amatda.presentation.view.home.HomeViewPagerModel
import com.turtle.amatda.presentation.view.itemselect.ItemSelectViewModel
import com.turtle.amatda.presentation.view.setting.SettingViewModel
import com.turtle.amatda.presentation.view.todo.TodoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewPagerModel::class)
    abstract fun bindsTodoMainViewModel(viewModel: HomeViewPagerModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TodoViewModel::class)
    abstract fun bindsTodoViewModel(viewModel: TodoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun bindsSettingViewModel(viewModel: SettingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ItemSelectViewModel::class)
    abstract fun bindsItemSelectViewModel(viewModel: ItemSelectViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DateViewModel::class)
    abstract fun bindsDateViewModel(viewModel: DateViewModel): ViewModel
}