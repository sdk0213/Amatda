package com.turtle.amatda.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.turtle.amatda.ui.setting.SettingFragment
import com.turtle.amatda.ui.todo.TodoFragment
import javax.inject.Inject

class AmatdaFragmentStateAdapter @Inject constructor(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> TodoFragment()
            else -> SettingFragment()
        }
    }
}