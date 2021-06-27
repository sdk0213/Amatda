package com.turtle.amatda.presentation.view.main

import androidx.work.Configuration
import com.turtle.amatda.presentation.di.DaggerAppComponent
import com.turtle.amatda.presentation.workers.WorkerFactory
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun applicationInjector(): AndroidInjector<out DaggerApplication?> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }

    /************************************* 칠판 *********************************************/
    /**
     * TODO: 개념 체크리스트
     *     1. di 에서 fragment 인지, activity 잘 상속받고 있는것인지
     *     2. lifecycleowner livedata를 직접 연결하는거랑 binding.lifecycleowner 랑 무슨 차이인지 확인
     */

    /**
     * TODO: 개발 체크리스트
     *     1. 네비게이션 컴포넌트 사용하여 원래 main 으로 돌아오게 하기
     *       - 현재 새로 앱을 켰을때랑 동일하게 앱이 홈화면으로 이동함
     *       - carrier 에서 시작했으면 carrier 에서 끝나야함
     *       - carrier 에서 값을 추가하고 종료했으면 백스텍으로 남기면 안됨
     *     2. 가방 추가
     *       - 캐리어 가방 이름 추가하기
     *       - 가방 추가 방식 한 화면으로 변경(추후에)
     *
     */

    /**************************************************************************************/
}