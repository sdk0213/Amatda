package com.turtle.amatda.ui

import android.content.Intent
import android.os.Bundle
import com.turtle.amatda.R
import com.turtle.amatda.databinding.ActivityMainBinding
import com.turtle.amatda.ui.main.AmatdaFragment
import dagger.Lazy
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var binding: Lazy<ActivityMainBinding>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.get()

        if (!isTaskRoot &&
            intent?.run {
                hasCategory(Intent.CATEGORY_LAUNCHER)
                action?.equals(Intent.ACTION_MAIN)
            } == true
        ) {
            finish()
            return
        }

        savedInstanceState ?: run {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, AmatdaFragment())
                .commit()
        }

    }

}