package com.turtle.amatda.presentation.view.carrier_item

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.ViewGroup
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

    val viewList = arrayListOf<TextView>() // 현재 생성된 아이템 View 에 관한 List
    var viewIdNewClicked = Date()     // 유저가 새롭게 선택한 View Id (Tag)
    var viewIdHasBeenClicked = Date() // 이미 선택되어있는 View Id (Tag)

    private val containerStartY: () -> Int
        get() = {
            val location = IntArray(2)
            binding.itemContainer.getLocationOnScreen(location)
            location[1]
        }

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

        // 아이템 변경 관찰자
        // viewList : 기존 물품 List (View)
        // itemList : 변경된 물품 List (Item)
        viewModel.itemList.observe(viewLifecycleOwner) { itemList ->
            // Item 에 관한 기존 View가 있다면 해당 View는 유지한 상태에서 제거 및 추가
            // 즉, 이미 만들어져있는 View 는 굳이 다시 재생성하지 않는다
            val keepViewList = arrayListOf<TextView>() // 유지해야하는 View 개수
            val removeViewList = arrayListOf<TextView>() // 제거해야하는 View 개수
            val makeItemList = arrayListOf<Item>() // 생성해야 하는 아이템 개수
            removeViewList.addAll(viewList)
            makeItemList.addAll(itemList)
            for (i in 0 until viewList.size) {
                for (j in 0 until itemList.size) {
                    // 기존것과 동일하다면 View를 유지하며 변경되었다면 View 제거후 다시 생성
                    if (viewList[i].tag as Date == itemList[j].id && viewList[i].text == itemList[j].name) {
                        keepViewList.add(viewList[i])
                        makeItemList.remove(itemList[j])
                        break
                    }
                }
            }
            // ex) 1. viewList : [1, 2, 3, 4] / itemLIst : [4, 5] ( '[4]'는 유지해야하는 View )
            // ex) 2. viewList : [4] / itemLIst : [5]
            viewList.retainAll(keepViewList)
            // ex) 3. removeList : [1, 2, 3]
            removeViewList.removeAll(keepViewList)
            // ex) 4. View 에서 removeList 지우기
            removeViewList.forEach { binding.itemContainer.removeView(it) }
            // ex) 5. viewList : [4] / itemLIst : [5] (View 에 itemList 추가)
            makeItemList.forEach { makeItemView(it) }
        }

        // 아이템 클릭 관찰자
        viewModel.isItemClicked.observe(viewLifecycleOwner) { isItemClicked ->
            if (viewIdHasBeenClicked != viewIdNewClicked) {
                viewList
                    .filter { it.tag == viewIdHasBeenClicked }
                    .map { it.isSelected = false }
                viewIdHasBeenClicked = viewIdNewClicked
                viewModel.itemIdCurrentClicked = viewIdHasBeenClicked
            }
            if (isItemClicked) {
                viewList
                    .filter { it.tag == viewIdHasBeenClicked }
                    .map {
                        binding.itemName.text = it.text.toEditable()
                        binding.itemName.setSelection(binding.itemName.text.toString().length)
                    }
            } else {
                viewList.map { it.isSelected = false }
                binding.itemName.text = "".toEditable()
                binding.itemName.clearFocus()
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
    private fun makeItemView(item: Item) =
        (layoutInflater.inflate(R.layout.carrier_item, null) as TextView).apply {
            layoutParams = ViewGroup.LayoutParams(
                250,
                250
            )
            tag = item.id // tag에 id 를 삽입하여 어떤 아이템인지 구분
            text = item.name
            x = item.position_x
            y = item.position_y
            binding.itemContainer.addView(this) // 뷰에 붙히기
            itemTouchListener(this) // 터치 리스너 받기
            viewList.add(this) // 텍스트뷰 관리 리스트에 추가
            if(tag as Date == viewIdHasBeenClicked){
                isSelected = true
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
                    }
                    // todo : view.isSelected 안쪽에 넣고 다른 아이템을 클릭할때 해당아이템으로 이동하기
                    binding.editItem.x = view.x - (binding.editItem.width/2) + (view.width/2)
                    binding.editItem.y = view.y - binding.editItem.height - binding.editSizeHeightUp.height - 25.0f
                    // width down
                    binding.editSizeWidthDown.x = view.x - 25.0f - binding.editSizeWidthDown.width
                    binding.editSizeWidthDown.y = view.y + view.height / 2 - binding.editSizeWidthDown.height / 2

                    // width up
                    binding.editSizeWidthUp.x = view.x + view.width + 25.0f
                    binding.editSizeWidthUp.y = view.y + view.height / 2 - binding.editSizeWidthUp.height / 2

                    // height up
                    binding.editSizeHeightUp.x = view.x - (binding.editSizeHeightUp.width/2) + (view.width/2)
                    binding.editSizeHeightUp.y = view.y - binding.editSizeHeightUp.height - 25.0f

                    // height down
                    binding.editSizeHeightDown.x = view.x - (binding.editSizeHeightUp.width/2) + (view.width/2)
                    binding.editSizeHeightDown.y = view.y + view.height + 25.0f
                }
                MotionEvent.ACTION_UP -> {
                    viewIdNewClicked = view.tag as Date
                    if (view.isSelected) {
                        viewModel.moveItem(
                            item_id = viewIdNewClicked,
                            itemName = textView.text.toString(),
                            item_pos_x = view.x,
                            item_pos_y = view.y
                        )

                        binding.editItem.bringToFront()
                        binding.editSizeWidthDown.bringToFront()
                        binding.editSizeWidthUp.bringToFront()
                        binding.editSizeHeightUp.bringToFront()
                        binding.editSizeHeightDown.bringToFront()
                    }
                    view.isSelected = true
                    viewModel.itemIsClicked()
                }
            }
            view.invalidate()
            true
        }
    }

    private fun onBackPressed() =
        requireActivity().onBackPressedDispatcher.addCallback(
            binding.lifecycleOwner!!,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (viewModel.isItemClicked.value == true) {
                        viewList
                            .map { it.isSelected = false }
                        viewModel.itemIsUnClicked()
                    } else {
                        findNavController().navigateUp()
                    }
                }
            })
}