package com.turtle.amatda.presentation.view.carrier_item

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierItemBinding
import com.turtle.amatda.presentation.view.base.BaseFragment


class CarrierItemFragment : BaseFragment<CarrierItemViewModel, FragmentCarrierItemBinding>(R.layout.fragment_carrier_item) {

    private val args: CarrierItemFragmentArgs by navArgs()

    private val arrayOfTextView: MutableList<TextView>
        get() = mutableListOf<TextView>()

    private val containerStartY: () -> Int
        get() = {
            val location = IntArray(2)
            binding.itemContainer.getLocationOnScreen(location)
            location[1]
        }

    override fun init() {
        viewModel.carrier = args.carrier
        addItemClickListener()
    }

    private fun addItemClickListener() =
        binding.setAddClick {
            binding.itemContainer.addView(newItem())
            viewModel.addItem()
        }

    @SuppressLint("ClickableViewAccessibility")
    private fun itemTouchListener(textView: TextView) {
        textView.setOnTouchListener{ view, event ->
            val x = event.rawX
            val y = event.rawY
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_MOVE -> {
                    view.x = x - view.width / 2.0f
                    view.y = y - view.height / 2.0f - containerStartY()
                }
            }
            view.invalidate()
            true
        }
    }

    private fun newItem() : View {
        val tv = layoutInflater.inflate(R.layout.carrier_item, null) as TextView
        tv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        tv.text = binding.itemName.text.toString()
        arrayOfTextView.add(tv)
        itemTouchListener(tv)
        return tv
    }


}