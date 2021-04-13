package com.turtle.amatda.ui.todo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.databinding.FragmentTodoBinding
import com.turtle.amatda.di.qualifier.ApplicationContext
import com.turtle.amatda.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module
class TodoModule {

    //데이터 바인딩 클래스 제공
    @Provides
    @FragmentScope
    fun provideFragmentTodoBinding(@ApplicationContext context: Context): FragmentTodoBinding {
        return FragmentTodoBinding.inflate(LayoutInflater.from(context), null, false)
    }

    //RecyclerView용 레이아웃 매니저
    @Provides
    @FragmentScope
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager{
        return object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }

}