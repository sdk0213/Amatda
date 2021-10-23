package com.turtle.amatda.presentation.android.di

import com.turtle.amatda.presentation.android.di.module.*
import com.turtle.amatda.presentation.android.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityModule::class,  // 액티비티 스코프 모듈
        AppModule::class, // 애플리케이션 스코프 모듈
        DatabaseModule::class,
        LocalDataModule::class,
        RemoteDataModule::class,
        NetModule::class,
        MapperModule::class,
        RepositoryModule::class,
        UseCaseModule::class,
        AssistedWorkerInjectModule::class,
        WorkerBindingModule::class,
        UtilModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<App>
}