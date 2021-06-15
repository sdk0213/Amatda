package com.turtle.amatda.presentation.view.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.turtle.amatda.presentation.view.setting.SettingFragment
import com.turtle.amatda.presentation.view.todo.TodoFragment
import javax.inject.Inject

class HomeFragmentStateAdapter constructor(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TodoFragment()
            else -> SettingFragment()
        }
    }
}