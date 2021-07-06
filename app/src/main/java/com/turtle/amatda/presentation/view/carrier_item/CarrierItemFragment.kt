package com.turtle.amatda.presentation.view.carrier_item

import android.annotation.SuppressLint
import android.text.TextUtils
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.textview.MaterialTextView
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierItemBinding
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.presentation.view.base.BaseFragment
import java.util.*


class CarrierItemFragment :
    BaseFragment<CarrierItemViewModel, FragmentCarrierItemBinding>(R.layout.fragment_carrier_item) {

    private val args: CarrierItemFragmentArgs by navArgs()
    private val arrayOfTextView = arrayListOf<TextView>()

    var currentClickedViewId = Date()

    private val containerStartY: () -> Int
        get() = {
            val location = IntArray(2)
            binding.itemContainer.getLocationOnScreen(location)
            location[1]
        }

    override fun init() {
        viewModel.apply {
            carrier = args.carrier
            getCarrerItems(viewModel.carrier.id)
        }
        observeItemList()
        addItemClickListener()
        saveItem()
        onBackPressOnFragment()
    }

    private fun addItemClickListener() =
        binding.itemNameView.setEndIconOnClickListener {
            if (TextUtils.isEmpty(binding.itemName.text.toString())) {
                return@setEndIconOnClickListener
            }
            makeItemView(
                Item(
                    id = Date(),
                    name = binding.itemName.text.toString(),
                    carrier_id = args.carrier.id
                )
            )
        }

    private fun saveItem() {
        arrayOfTextView.forEach {
            viewModel.arrayOfItem.add(
                Item(
                    id = it.tag as Date,
                    name = it.text.toString(),
                    position_x = it.x,
                    position_y = it.y,
                    carrier_id = args.carrier.id
                )
            )
        }
        viewModel.saveCarrier()
    }

    private fun observeItemList() =
        viewModel.itemList.observe(this) {
            it.forEach { item ->
                makeItemView(item)
            }
        }

    // 아이템 View 생성
    private fun makeItemView(item: Item) =
        (layoutInflater.inflate(R.layout.carrier_item, null) as MaterialTextView).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            tag = item.id
            text = item.name
            x = item.position_x
            y = item.position_y
            arrayOfTextView.add(this) // 텍스트뷰 관리 리스트에 추가
            binding.itemContainer.addView(this) // 뷰에 붙히기
            itemTouchListener(this) // 터치 리스너 받기
            saveItem()
        }

    @SuppressLint("ClickableViewAccessibility")
    private fun itemTouchListener(textView: TextView) {
        var firstTouchRelativeX = 0f
        var firstTouchRelativeY = 0f
        textView.setOnTouchListener { view, event ->
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    firstTouchRelativeX = event.x
                    firstTouchRelativeY = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    if (view.isSelected) {
                        view.x = event.rawX - firstTouchRelativeX
                        view.y = event.rawY - firstTouchRelativeY - containerStartY()
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (currentClickedViewId != view.tag as Date) {
                        arrayOfTextView
                            .filter { it.tag == currentClickedViewId }
                            .map { it.isSelected = false }
                        currentClickedViewId = view.tag as Date
                    }
                    view.isSelected = true
                    viewModel.itemClicked()

                    if (view.isSelected) {
                        saveItem()
                    }
                }
            }
            view.invalidate()
            true
        }
    }

    private fun onBackPressOnFragment() =
        requireActivity().onBackPressedDispatcher.addCallback(
            binding.lifecycleOwner!!,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewModel.itemClicked.value == true) {
                        arrayOfTextView
                            .map { it.isSelected = false }
                        viewModel.itemUnlicked()
                    } else {
                        findNavController().navigateUp()
                    }
                }
            })
}