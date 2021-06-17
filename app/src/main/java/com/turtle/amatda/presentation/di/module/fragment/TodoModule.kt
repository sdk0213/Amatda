package com.turtle.amatda.presentation.di.module.fragment

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTodoBinding
import com.turtle.amatda.presentation.di.qualifier.ApplicationContext
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.main.MainActivity
import com.turtle.amatda.presentation.view.todo.TodoFragment
import dagger.Module
import dagger.Provides

@Module
class TodoModule {

    //데이터 바인딩 클래스 제공
    @Provides
    @FragmentScope
    fun provideFragmentTodoBinding(activity: MainActivity): FragmentTodoBinding {
        return DataBindingUtil.inflate<FragmentTodoBinding>(
            activity.layoutInflater,
            R.layout.fragment_todo,
            null,
            false
        )
    }

    //RecyclerView용 레이아웃 매니저
    @Provides
    @FragmentScope
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager {
        return object : LinearLayoutManager(context) {
            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
                return RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }

    //Navigation 컴포넌트에서 목적지 간 이동을 담당하는 NavController
    @Provides
    @FragmentScope
    fun provideNavController(fragment: TodoFragment): NavController {
        return NavHostFragment.findNavController(fragment)
    }

}