package com.turtle.amatda.presentation.view.carrier_item

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierItemBinding
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.presentation.utilities.extensions.toEditable
import com.turtle.amatda.presentation.view.base.BaseFragment
import java.util.*


class CarrierItemFragment :
    BaseFragment<CarrierItemViewModel, FragmentCarrierItemBinding>(R.layout.fragment_carrier_item) {

    private val args: CarrierItemFragmentArgs by navArgs()

    private val viewItemList = arrayListOf<TextView>() // 현재 생성된 아이템 View 에 관한 List
    private var viewIdNewClicked = Date()     // 유저가 새롭게 선택한 View Id (Tag)
    private var viewIdHasBeenClicked = Date() // 이미 선택되어있는 View Id (Tag)

    private val containerStartY: () -> Int
        get() = {
            val location = IntArray(2)
            binding.itemContainer.getLocationOnScreen(location)
            location[1]
        }

    private val viewDimenOfToolTip = 25.0f

    override fun init() {
        viewModel.apply {
            binding.viewModel = this
            carrier = args.carrier
            getCarrierItems(viewModel.carrier.id)
        }
        observer()
        listener()
        onBackPressed()
    }

    private fun observer() {

        viewModel.removeItemList.observe(viewLifecycleOwner) { removeItemList ->
            val removeTextViewList = arrayListOf<TextView>()
            for (i in 0 until viewItemList.size) {
                for (j in 0 until removeItemList.size) {
                    if(viewItemList[i].tag as Date == removeItemList[j].id) {
                        removeTextViewList.add(viewItemList[i])
                        binding.itemContainer.removeView(viewItemList[i])
                        break
                    }
                }
            }
            viewItemList.removeAll(removeTextViewList)
        }

        viewModel.makeItemList.observe(viewLifecycleOwner) { makeItemList ->
            makeItemList.forEach { makeItemView(it) }
        }

        // 아이템 클릭 관찰자
        viewModel.isItemClicked.observe(viewLifecycleOwner) { isItemClicked ->
            if (viewIdHasBeenClicked != viewIdNewClicked) {
                viewItemList
                    .filter { it.tag == viewIdHasBeenClicked }
                    .map { it.isSelected = false }
                viewIdHasBeenClicked = viewIdNewClicked
                viewModel.itemIdCurrentClicked = viewIdHasBeenClicked
                viewModel.itemResizeIsUnClicked()
            }
            if (isItemClicked) {
                viewItemList
                    .filter { it.tag == viewIdHasBeenClicked }
                    .map {
                        binding.itemName.text = it.text.toEditable()
                        binding.itemName.setSelection(binding.itemName.text.toString().length)
                        updateToolTipViewPositioning(it.x, it.y, it.width, it.height)
                    }
            } else {
                viewItemList.map { it.isSelected = false }
                binding.itemName.text = "".toEditable()
                binding.itemName.clearFocus()
            }
        }

        // 아이템 리사이즈 클릭 관찰자
        viewModel.isItemResizeClicked.observe(viewLifecycleOwner) { itemResizeClicked ->
            viewItemList
                .filter { it.tag == viewIdHasBeenClicked }
                .map {
                    if (itemResizeClicked) {
                        binding.editItem.y =
                            it.y - binding.editItem.height - binding.editIncreaseHeight.height - viewDimenOfToolTip
                    } else {
                        binding.editItem.y = it.y - binding.editItem.height - viewDimenOfToolTip
                    }
                }
        }
    }

    private fun listener() {
        // Custom Listener 를 View에 바인딩하는 방법을 모르겠다... OnClick 에다가 주기에는 전체 View 에 대한 Listener 이기 때문에 잘못되었다.
        // 그래서 우선은 Fragment 에서 생성
        binding.itemNameView.setEndIconOnClickListener {
            if (viewModel.isItemClicked.value == true) {
                binding.itemName.text.toString().let {
                    if (!TextUtils.isEmpty(it)) viewModel.editItem(it)
                }
            } else {
                binding.itemName.text.toString().let {
                    if (!TextUtils.isEmpty(it)) viewModel.addItem(it)
                }
            }
        }
    }

    // 아이템 View 동적 생성
    private fun makeItemView(item: Item) : TextView =
        (layoutInflater.inflate(R.layout.carrier_item, null) as TextView).apply {
            width = item.width
            height = item.height
            tag = item.id // tag에 id 를 삽입하여 어떤 아이템인지 구분
            text = item.name
            x = item.position_x
            y = item.position_y
            binding.itemContainer.addView(this) // 뷰에 붙히기
            itemTouchListener(this) // 터치 리스너 받기
            viewItemList.add(this) // 텍스트뷰 관리 리스트에 추가
            if (tag as Date == viewIdHasBeenClicked) {
                isSelected = true
                updateToolTipViewPositioning(item.position_x, item.position_y, item.width, item.height) // 아직 생성되지 않은 뷰를 참조하면 모든 좌표 값이 0 으로 출력되기에 Item 값으로 변경하였음
            }
        }

    // 아이템 View 터치 리스너
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
                        updateToolTipViewPositioning(textView.x, textView.y, textView.width, textView. height)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    viewIdNewClicked = view.tag as Date
                    if (view.isSelected) {
                        viewModel.updateMove(
                            item_id = viewIdNewClicked,
                            item_pos_x = view.x,
                            item_pos_y = view.y
                        )
                    }
                    view.bringToFront()
                    binding.editItem.bringToFront()
                    binding.editDecreaseWidth.bringToFront()
                    binding.editIncreaseWidth.bringToFront()
                    binding.editIncreaseHeight.bringToFront()
                    binding.editDecreaseHeight.bringToFront()
                    view.isSelected = true
                    viewModel.itemIsClicked()
                }
            }
            view.invalidate()
            true
        }
    }

    // 아이템 툴팁 포지셔닝
    private fun updateToolTipViewPositioning(
        x: Float,
        y: Float,
        width: Int,
        height: Int
    ) {

        binding.editItem.x = x - (binding.editItem.width / 2) + (width / 2)
        if (viewModel.isItemResizeClicked.value == true) {
            binding.editItem.y =
                y - binding.editItem.height - binding.editIncreaseHeight.height - viewDimenOfToolTip
        } else {
            binding.editItem.y = y - binding.editItem.height - viewDimenOfToolTip
        }

        // width down
        binding.editDecreaseWidth.x = x - viewDimenOfToolTip - binding.editDecreaseWidth.width
        binding.editDecreaseWidth.y =
            y + height / 2 - binding.editDecreaseWidth.height / 2

        // width up
        binding.editIncreaseWidth.x = x + width + viewDimenOfToolTip
        binding.editIncreaseWidth.y =
            y + height / 2 - binding.editIncreaseWidth.height / 2

        // height up
        binding.editIncreaseHeight.x =
            x - (binding.editIncreaseHeight.width / 2) + (width / 2)
        binding.editIncreaseHeight.y =
            y - binding.editIncreaseHeight.height - viewDimenOfToolTip

        // height down
        binding.editDecreaseHeight.x =
            x - (binding.editIncreaseHeight.width / 2) + (width / 2)
        binding.editDecreaseHeight.y = y + height + viewDimenOfToolTip

    }

    private fun onBackPressed() =
        requireActivity().onBackPressedDispatcher.addCallback(
            binding.lifecycleOwner!!,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewModel.isItemClicked.value == true) {
                        viewItemList
                            .map { it.isSelected = false }
                        viewModel.itemIsUnClicked()
                    } else {
                        findNavController().navigateUp()
                    }
                }
            })
}