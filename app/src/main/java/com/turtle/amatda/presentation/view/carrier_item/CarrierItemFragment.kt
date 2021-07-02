package com.turtle.amatda.presentation.view.carrier_item

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierItemBinding
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.presentation.view.base.BaseFragment


class CarrierItemFragment : BaseFragment<CarrierItemViewModel, FragmentCarrierItemBinding>(R.layout.fragment_carrier_item) {

    private val args: CarrierItemFragmentArgs by navArgs()
    private val arrayOfTextView = arrayListOf<TextView>()
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
        addSaveClickListener()
    }

    private fun addItemClickListener() =
        binding.itemNameView.setEndIconOnClickListener {
            makeItem(itemName = binding.itemName.text.toString())
        }

    private fun addSaveClickListener() =
        binding.setClickSaveCarrier {
            arrayOfTextView.forEach{
                viewModel.arrayOfItem.add(
                    Item(
                        id = it.id.toLong(),
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
        viewModel.itemList.observe(this){
            it.forEach { item ->
                makeItem(itemName = item.name, x = item.position_x, y = item.position_y)
            }
        }

    // 아이템 View 생성
    private fun makeItem(itemName: String, x: Float = 0f, y: Float = 0f) =
        (layoutInflater.inflate(R.layout.carrier_item, null) as TextView).apply{
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            text = itemName
            this.x = x
            this.y = y
            arrayOfTextView.add(this) // 텍스트뷰 관리 리스트에 추가
            binding.itemContainer.addView(this) // 뷰에 붙히기
            itemTouchListener(this) // 터치 리스너 받기
        }

    @SuppressLint("ClickableViewAccessibility")
    private fun itemTouchListener(textView: TextView) {
        textView.setOnTouchListener{ view, event ->
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_MOVE -> {
                    view.x = event.rawX - view.width / 2.0f
                    view.y = event.rawY - view.height / 2.0f - containerStartY()
                }
            }
            view.invalidate()
            true
        }
    }

}