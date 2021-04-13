package com.turtle.amatda.ui

import android.content.Intent
import android.os.Bundle
import com.turtle.amatda.R
import com.turtle.amatda.databinding.ActivityMainBinding
import com.turtle.amatda.ui.todo.TodoFragment
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    //바인딩 클래스 주입
    @Inject
    lateinit var binding: Lazy<ActivityMainBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isTaskRoot &&
            intent?.run {
                hasCategory(Intent.CATEGORY_LAUNCHER)
                action?.equals(Intent.ACTION_MAIN)
            } == true
        ) {
            finish()
            return
        }

        binding.get().lifecycleOwner = this

        savedInstanceState ?: run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, TodoFragment())
                .commit()
        }

    }

}