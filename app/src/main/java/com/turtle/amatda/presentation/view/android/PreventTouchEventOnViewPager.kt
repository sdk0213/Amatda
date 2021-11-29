package com.turtle.amatda.presentation.view.android

import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class PreventTouchEventOnViewPager : RecyclerView.OnItemTouchListener {

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(true)
        }
        return false

    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

}