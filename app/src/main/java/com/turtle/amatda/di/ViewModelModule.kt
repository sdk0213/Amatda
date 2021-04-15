package com.turtle.amatda.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turtle.amatda.ui.main.AmatdaViewModel
import com.turtle.amatda.ui.setting.SettingViewModel
import com.turtle.amatda.ui.todo.TodoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AmatdaViewModel::class)
    abstract fun bindsTodoMainViewModel(viewModel: AmatdaViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TodoViewModel::class)
    abstract fun bindsTodoViewModel(viewModel: TodoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun bindsSettingViewModel(viewModel: SettingViewModel): ViewModel
}