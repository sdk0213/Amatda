package com.turtle.amatda.presentation.android.di

import com.turtle.amatda.presentation.android.App
import com.turtle.amatda.presentation.android.di.module.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidActivityModule::class,  // 액티비티 스코프 모듈
        AndroidServiceModule::class, // 서비스 모듈
        AndroidBroadCastReceiverModule::class, // 브로드 캐스트 리시버 모듈
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