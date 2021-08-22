package com.turtle.amatda.presentation.view.carrier_item

import android.annotation.SuppressLint
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.*
import android.view.View.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.mauker.materialsearchview.MaterialSearchView
import com.google.android.material.tabs.TabLayout
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierItemBinding
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.model.Pocket
import com.turtle.amatda.presentation.utilities.extensions.toEditable
import com.turtle.amatda.presentation.view.base.BaseFragment
import java.util.*


class CarrierItemFragment :
    BaseFragment<CarrierItemViewModel, FragmentCarrierItemBinding>(R.layout.fragment_carrier_item) {

    private val args: CarrierItemFragmentArgs by navArgs()

    private val viewItemList = arrayListOf<TextView>() // 현재 생성된 아이템 View 에 관한 List
    private val viewItemMap = mutableMapOf<TextView, Item>() // 현재 생성된 View와 해당 Item의 Map
    private val viewItemCountMap = mutableMapOf<TextView, TextView>() // 현재 생성된 아이템 View 에 관한 Map
    private val viewItemPositionMap =
        mutableMapOf<TextView, ImageView>() // 현재 생성된 아이템 View 에 관한 Map
    private val viewItemColorMap = mutableMapOf<TextView, Long>() // 현재 생성된 아이템 View 에 관한 Color Map
    private val viewPocketAndMenuItemViewIdMap =
        mutableMapOf<Int, Pocket>() // MenuItemViewId 과 Pocket Map , ViewId를 사용하여 View의 정보를 얻고 이랑 대칭한 Pocket 정보를 얻기위함
    private var viewIdNewClicked = Date()     // 유저가 새롭게 선택한 View Id (Tag)
    private var viewIdHasBeenClicked = Date() // 이미 선택되어있는 View Id (Tag)
    private val mapOfPocketAndItemSize = mutableMapOf<Pocket, Int>() // 주머니와 주머니의 아이템개수에 관한 Map

    private val arrayOfAllItem = arrayListOf<String>()
    private val mapOfPocketIdAndPocketName = mutableMapOf<Date,String>()
    private val mapOfPocketIdAndCarrierName = mutableMapOf<Date,String>()

    private var viewCurrentClickedMenuItem = 0

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
            currentCarrier = args.carrier
            getAllItem()
            getPocketItems()
        }

        binding.navigationView.getHeaderView(0).findViewById<TextView>(R.id.nav_header_title).text = " - ${viewModel.currentCarrier.name}"
        observer()
        listener()
        onBackPressed()
    }

    private fun observer() {

        // 주머니가 존재한다면 해당 메뉴 생성
        viewModel.isPocketExist.observe(viewLifecycleOwner) { isPocketExist ->
            binding.guideViewContainer.visibility = if (isPocketExist) GONE else VISIBLE
            binding.tabLayout.visibility = if (isPocketExist) VISIBLE else GONE
            binding.itemContainer.visibility = if (isPocketExist) VISIBLE else GONE
            binding.topAppBar.menu.setGroupVisible(0, isPocketExist)
        }

        // 주머니 정보를 받아서 MenuItem View 그리기
        viewModel.pocketList.observe(viewLifecycleOwner) { pocketList ->
            binding.navigationView.menu.findItem(R.id.nav_pocket_submenu).subMenu.clear() // navigation Item 초기화
            viewPocketAndMenuItemViewIdMap.clear() // 주머니 정보 Map 초기화
            pocketList.map {
                val menuItemViewId = generateViewId()
                viewPocketAndMenuItemViewIdMap[menuItemViewId] = it
                binding.navigationView.menu.findItem(R.id.nav_pocket_submenu).subMenu
                    .add(
                        R.id.nav_pocket_group,
                        menuItemViewId,
                        MenuItem.SHOW_AS_ACTION_ALWAYS,
                        it.name
                    )
                    .apply {
                        setIcon(R.drawable.flaticon_com_ic_book_bag_with_pockets)
                        if (it.id == viewModel.currentPocket.id) {
                            isChecked = true
                            isCheckable = true
                        }
                    }
            }
            binding.topAppBar.title = viewModel.currentPocket.name
            updateViewMenuItemActionView()
        }

        // 제거해야할 아이템 이름 View 목록
        viewModel.removeItemList.observe(viewLifecycleOwner) { removeItemList ->
            val removeTextViewList = arrayListOf<TextView>()
            for (i in 0 until viewItemList.size) {
                for (j in 0 until removeItemList.size) {
                    if (viewItemList[i].tag as Date == removeItemList[j].id) {
                        removeTextViewList.add(viewItemList[i])
                        binding.itemContainer.removeView(viewItemList[i])
                        binding.itemContainer.removeView(viewItemCountMap[viewItemList[i]])
                        binding.itemContainer.removeView(viewItemPositionMap[viewItemList[i]])
                        break
                    }
                }
            }
            removeTextViewList.map {
                viewItemMap[it]
                viewItemCountMap[it]
                viewItemPositionMap[it]
                viewItemColorMap[it]
            }
            viewItemList.removeAll(removeTextViewList)
        }

        // 생성해야할 아이템 이름 View 목록
        viewModel.makeItemList.observe(viewLifecycleOwner) { makeItemList ->
            makeItemList.forEach { item ->
                (layoutInflater.inflate(R.layout.carrier_item, null) as TextView).apply {
                    viewItemMap[this] = item
                    viewItemColorMap[this] = item.color
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
                        selectItem(this)
                        updateToolTipViewPositioning(
                            item.position_x,
                            item.position_y,
                            item.width,
                            item.height
                        ) // 아직 생성되지 않은 뷰를 참조하면 모든 좌표 값이 0 으로 출력되기에 Item 값으로 변경하였음
                        binding.itemCount.text = item.count.toString()
                    }
                    // 생성해야할 아이템 개수 View 목록
                    viewItemCountMap[this] =
                        (layoutInflater.inflate(
                            R.layout.carrier_item_count,
                            null
                        ) as TextView).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            text = item.count.toString()
                            tag = item.id // tag에 id 를 삽입하여 어떤 아이템인지 구분
                            x = item.position_x + item.width - 60
                            y = item.position_y + item.height - 60
                            binding.itemContainer.addView(this) // 뷰에 붙히기
                        }
                    // 아이템 위치 표시 View 목록
                    viewItemPositionMap[this] =
                        (layoutInflater.inflate(
                            R.layout.carrier_item_position,
                            null
                        ) as ImageView).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                60,
                                60
                            )
                            when (item.item_place) {
                                0 -> {
                                    backgroundTintList = ColorStateList.valueOf(0xFFFFFFFF.toInt())
                                    setBackgroundResource(R.drawable.flaticon_com_ic_pos_linenumber_1)
                                }
                                1 -> setBackgroundResource(R.drawable.flaticon_com_ic_pos_linenumber_2)
                                2 -> setBackgroundResource(R.drawable.flaticon_com_ic_pos_linenumber_3)
                            }
                            x = item.position_x + 20
                            y = item.position_y + 20
                            binding.itemContainer.addView(this) // 뷰에 붙히기
                        }

                    setItemBackgroundColorByPlace(this, item)
                }
            }
            updateViewBringToFrontOrderByPlace()
        }

        // 아이템 클릭 관찰자
        viewModel.isItemClicked.observe(viewLifecycleOwner) { isItemClicked ->
            // 기존에 선택된 것과 다르다면 이전 것은 선택 해제
            if (viewIdHasBeenClicked != viewIdNewClicked) {
                unSelectItem()
                viewIdHasBeenClicked = viewIdNewClicked
                viewModel.itemIdCurrentClicked = viewIdHasBeenClicked
            }
            if (isItemClicked) {
                viewItemList.find { it.tag == viewIdHasBeenClicked }?.apply {
                    binding.itemName.text = text.toEditable()
                    binding.itemName.setSelection(binding.itemName.text.toString().length)
                    binding.itemCount.text = viewItemCountMap[this]?.text
                    updateToolTipViewPositioning(x, y, width, height)
                }
            } else {
                unSelectItem()
                binding.itemName.text = "".toEditable()
                binding.itemName.clearFocus()
            }
        }

        // 아이템 리사이즈 클릭 관찰자
        viewModel.isItemResizeClicked.observe(viewLifecycleOwner) {
            viewItemList.find { it.tag == viewIdHasBeenClicked }?.apply {
                updateToolTipViewPositioning(x, y, width, height)
            }
        }

        // 아이템 개수 조정 클릭 관찰자
        viewModel.isItemRecountClicked.observe(viewLifecycleOwner) {
            viewItemList.find { it.tag == viewIdHasBeenClicked }?.apply {
                updateToolTipViewPositioning(x, y, width, height)
            }
        }

        // @deprecated 사용하지 않음
        // 아이템 컬러 수정 클릭 관찰자
        viewModel.isItemRecolorClicked.observe(viewLifecycleOwner) {
            viewItemList.find { it.tag == viewIdHasBeenClicked }?.apply {
                updateToolTipViewPositioning(x, y, width, height)
            }
        }

        // 아이템 이름 수정 클릭 관찰자
        viewModel.isItemRenameClicked.observe(viewLifecycleOwner) {
            if (viewModel.isItemRenameClicked.value == true) {
                binding.itemNameView.visibility = VISIBLE
                binding.itemName.requestFocus()
                (mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                    binding.itemName,
                    0
                )
            } else {
                binding.itemNameView.visibility = GONE
                binding.itemName.clearFocus()
                (mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    binding.itemName.windowToken,
                    0
                )
            }
        }

        // 주머니 삭제 클릭 관찰자
        viewModel.isPocketDeleteClicked.observe(viewLifecycleOwner) { pocketDeleteClicked ->
            updateViewMenuItemActionView()
            binding.navigationView.menu.findItem(R.id.pocket_delete)
                .setTitle(
                    if (pocketDeleteClicked) getString(R.string.edit_pocket_delete_cancel) else getString(
                        R.string.edit_pocket_delete
                    )
                )
                .setIcon(if (pocketDeleteClicked) R.drawable.ic_baseline_cancel_24 else R.drawable.ic_baseline_delete_24)
                .setChecked(pocketDeleteClicked)
                .setCheckable(pocketDeleteClicked)
        }

        // 주머니 이름 변경 클릭 관찰자
        viewModel.isPocketRenameClicked.observe(viewLifecycleOwner) { pocketRenameClicked ->
            if (pocketRenameClicked == false) {
                binding.pocketNameView.visibility = GONE
                binding.pocketName.clearFocus()
                (mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    binding.pocketName.windowToken,
                    0
                )
            }
            updateViewMenuItemActionView()
            binding.navigationView.menu.findItem(R.id.pocket_rename)
                .setTitle(
                    if (pocketRenameClicked) getString(R.string.edit_pocket_rename_cancel) else getString(
                        R.string.edit_pocket_rename
                    )
                )
                .setIcon(if (pocketRenameClicked) R.drawable.ic_baseline_cancel_24 else R.drawable.ic_baseline_drive_file_rename_outline_24)
                .setChecked(pocketRenameClicked)
                .setCheckable(pocketRenameClicked)
        }

        viewModel.pocketAndItemSize.observe(viewLifecycleOwner) { listOfPocketAndItemSize ->
            mapOfPocketAndItemSize.clear()
            listOfPocketAndItemSize.map {
                mapOfPocketAndItemSize[it.pocket] = it.itemSize
            }
        }

        viewModel.allCarrierPocketList.observe(viewLifecycleOwner) { allCarrierAndPocket ->
            mapOfPocketIdAndCarrierName.clear()
            mapOfPocketIdAndPocketName.clear()
            allCarrierAndPocket.forEach{ carrierAndPocket ->
                carrierAndPocket.pockets.forEach { pocket ->
                    mapOfPocketIdAndCarrierName[pocket.id] = carrierAndPocket.carrier.name
                    mapOfPocketIdAndPocketName[pocket.id] = pocket.name
                }
            }
        }

        viewModel.allItemList.observe(viewLifecycleOwner) { listOfAllItem ->
            arrayOfAllItem.clear()
            listOfAllItem.forEach{ item ->
                // "새 물품 (캐리어가방 - 큰주머니 - 바깥쪽)",
                arrayOfAllItem.add("${item.name} (${mapOfPocketIdAndCarrierName[item.pocket_id]} - ${mapOfPocketIdAndPocketName[item.pocket_id]} - ${
                    when(item.item_place){
                        0 -> "안쪽"
                        1 -> "가운데 쪽"
                        else ->"바깥 쪽"
                    }
                })")
            }
        }

    }

    private fun listener() {
        // Custom Listener 를 View 에 바인딩하는 방법을 모르겠다... OnClick 에다가 주기에는 전체 View 에 대한 Listener 이기 때문에 잘못되었다.
        // 그래서 우선은 Fragment 에서 생성
        // 물품 이름 변경
        binding.itemNameView.setEndIconOnClickListener {
            binding.itemName.text.toString().let {
                if (!it.isNullOrEmpty()) viewModel.editItem(it)
                binding.itemNameView.visibility = GONE
                binding.itemName.clearFocus()
                (mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    binding.itemName.windowToken,
                    0
                )
            }
        }

        // 물품 이름 변경 - [키보드 확인]
        binding.itemName.setOnKeyListener { v, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    binding.itemName.text.toString().let {
                        if (!it.isNullOrEmpty()) viewModel.editItem(it)
                        binding.itemNameView.visibility = GONE
                        binding.itemName.clearFocus()
                        (mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                            binding.itemName.windowToken,
                            0
                        )
                        viewModel.itemRenameIsUnClicked()
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }

        // 주머니 이름 변경 리스너
        binding.pocketNameView.setEndIconOnClickListener {
            binding.pocketName.text.toString().let {
                if (!it.isNullOrEmpty()) viewModel.editPocket(
                    viewPocketAndMenuItemViewIdMap[viewCurrentClickedMenuItem]?.id ?: Date(), it
                )
                binding.pocketNameView.visibility = GONE
                binding.pocketName.clearFocus()
                (mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    binding.pocketName.windowToken,
                    0
                )
            }
        }

        // 주머니 이름 변경 리스너 - [키보드 확인]
        binding.pocketName.setOnKeyListener { v, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    binding.pocketName.text.toString().let {
                        if (!it.isNullOrEmpty()) viewModel.editPocket(
                            viewPocketAndMenuItemViewIdMap[viewCurrentClickedMenuItem]?.id
                                ?: Date(), it
                        )
                        binding.pocketNameView.visibility = GONE
                        binding.pocketName.clearFocus()
                        (mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                            binding.pocketName.windowToken,
                            0
                        )
                    }
                    true
                }
                else -> {
                    false
                }
            }
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_add_item -> {
                    viewModel.addItem("새 물품", binding.tabLayout.selectedTabPosition)
                    true
                }
                R.id.item_search -> {
                    binding.searchView.openSearch()
                    true
                }
                else -> {
                    true
                }
            }
        }

        binding.searchView.apply {
            adjustTintAlpha(0.85f)
            setShouldKeepHistory(false)
            setShouldAnimate(true)

            setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if(!arrayOfAllItem.any { it.contains(query) }){
                        showToast("검색된 물품이 없습니다.")
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    Log.d(TAG,"onQueryTextChange is worked")
                    return false
                }
            })

            setSearchViewListener(object : MaterialSearchView.SearchViewListener {
                override fun onSearchViewOpened() {
                    activity?.window?.statusBarColor =
                        ContextCompat.getColor(mContext, R.color.white_ish)

                    binding.searchView.clearSuggestions()
                    binding.searchView.addSuggestions(arrayOfAllItem)
                }

                override fun onSearchViewClosed() {
                    activity?.window?.statusBarColor =
                        ContextCompat.getColor(mContext, R.color.amatda_main)
                }
            })

            setOnItemClickListener { _, _, position, _ ->
                // 아무것도 하지 않는다.
                // todo : 아이템 클릭시 해당 아이템으로 이동은 추후에 개발
            }
        }

        binding.drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                viewModel.pocketAllUnClicked()
            }

            override fun onDrawerStateChanged(newState: Int) {
            }

        })

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.buttonClickGuideNavigationview.setOnClickListener {
            binding.drawerLayout.open()
        }

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewModel.itemIsUnClicked()
                viewItemMap.forEach {
                    setItemBackgroundColorByPlace(it.key, it.value)
                }
                when (tab?.position) {
                    0 -> binding.guideText2.text = getString(R.string.item_guide_text_1)
                    1 -> binding.guideText2.text = getString(R.string.item_guide_text_2)
                    2 -> binding.guideText2.text = getString(R.string.item_guide_text_3)
                }
                updateViewBringToFrontOrderByPlace()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        }
        )

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.back_to_home -> {
                    findNavController().navigateUp()
                }
                R.id.pocket_add -> {
                    viewModel.addCarrierPocket()
                    true
                }
                R.id.pocket_delete -> {
                    viewModel.pocketDeleteClicked()
                    true
                }
                R.id.pocket_rename -> {
                    viewModel.pocketRenameClicked()
                    true
                }
                else -> {
                    viewPocketAndMenuItemViewIdMap[menuItem.itemId]?.let { pocket ->
                        // 모든 menuItem isCheckable, isChecked =  false 처리하여 View 진행
                        viewPocketAndMenuItemViewIdMap.forEach {
                            binding.navigationView.menu.findItem(it.key).apply {
                                isCheckable = false
                                isChecked = false
                            }
                        }
                        menuItem.isChecked = true
                        menuItem.isCheckable = true
                        viewModel.currentPocket = pocket
                        binding.topAppBar.title = viewModel.currentPocket.name
                        viewModel.pocketIsChanged()
                    }
                    binding.drawerLayout.close()
                    true
                }
            }
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
                        viewItemPositionMap[view]?.x = view.x + 20
                        viewItemPositionMap[view]?.y = view.y + 20
                        viewItemCountMap[view]?.x = view.x + view.width - 60
                        viewItemCountMap[view]?.y = view.y + view.height - 60
                        updateToolTipViewPositioning(
                            view.x,
                            view.y,
                            view.width,
                            view.height
                        )
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
                    updateViewBringToFrontOrderByPlace()
                    selectItem(view)
                    viewModel.itemIsClicked()
                }
            }
            view.invalidate()
            true
        }
    }

    private fun unSelectItem() {

        (mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
            binding.itemName.windowToken,
            0
        )
        binding.itemNameView.visibility = GONE
        viewItemList.find { it.tag == viewIdHasBeenClicked }?.isSelected = false
        viewModel.itemResizeIsUnClicked()
        viewModel.itemRecountIsUnClicked()
        viewModel.itemRecolorIsUnClicked()
        viewModel.itemRenameIsUnClicked()
    }

    private fun selectItem(view: View) {
        view.isSelected = true
    }

    // 탭에 따라 설정 아이템 테두리, 위치, 색깔 등 설정
    private fun setItemBackgroundColorByPlace(textView: TextView, item: Item) {
        val colorList = arrayListOf(0xFF000000, 0xFF888888, 0xFFFFFFFF) // Black, Grey, White
        textView.background = GradientDrawable().apply {
            setColor(colorList[item.item_place].toInt())
            if (binding.tabLayout.selectedTabPosition == item.item_place) {
                setStroke(10, 0xFFFF6E40.toInt())
            } else {
                setStroke(1, 0xFF000000.toInt())
            }
            cornerRadius = 25f
        }
        when (item.item_place) {
            0 -> textView.setTextColor(0xFFFFFFFF.toInt())
            else -> textView.setTextColor(0xFF000000.toInt())
        }

        textView.isEnabled = binding.tabLayout.selectedTabPosition == item.item_place

        if (item.item_place <= binding.tabLayout.selectedTabPosition) {
            textView.visibility = VISIBLE
            viewItemPositionMap[textView]?.visibility = VISIBLE
            viewItemCountMap[textView]?.visibility = VISIBLE
        } else {
            textView.visibility = INVISIBLE
            viewItemPositionMap[textView]?.visibility = INVISIBLE
            viewItemCountMap[textView]?.visibility = INVISIBLE
        }
    }

    // 아이템 View 보이는 순서 위치에 따라 설정
    private fun updateViewBringToFrontOrderByPlace() {
        for (i: Int in 1..3) {
            viewItemMap.filter { it.value.item_place == i }
                .map {
                    it.key.bringToFront()
                    viewItemCountMap[it.key]?.bringToFront()
                    viewItemPositionMap[it.key]?.bringToFront()
                }
        }

        binding.editItemCountView.bringToFront()
        binding.editItemColorView.bringToFront()
        binding.editItemView.bringToFront()
        binding.editDecreaseWidth.bringToFront()
        binding.editIncreaseWidth.bringToFront()
        binding.editIncreaseHeight.bringToFront()
        binding.editDecreaseHeight.bringToFront()
        binding.editSpeechBubble.bringToFront()

    }

    // 현재 사용자가 선택한 주머니 관리에 따라서 설정하기
    private fun updateViewMenuItemActionView() {
        viewPocketAndMenuItemViewIdMap.forEach {
            binding.navigationView.menu.findItem(R.id.nav_pocket_submenu).subMenu
                .findItem(it.key)
                .setActionView(
                    if (viewModel.isPocketDeleteClicked.value == true) {
                        R.layout.menu_nav_pocket_delete
                    } else if (viewModel.isPocketRenameClicked.value == true) {
                        R.layout.menu_nav_pocket_rename
                    } else {
                        R.layout.menu_nav_pocket_item_count
                    }
                )
                .actionView.apply {
                    this.setOnClickListener { view ->
                        viewCurrentClickedMenuItem = view.id
                        if (viewModel.isPocketDeleteClicked.value == true) {
                            viewModel.deletePocket(
                                viewPocketAndMenuItemViewIdMap[view.id]?.id ?: Date()
                            )
                        } else if (viewModel.isPocketRenameClicked.value == true) {
                            binding.pocketNameView.visibility = VISIBLE
                            binding.pocketName.requestFocus()
                            (mContext.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                                binding.pocketName,
                                0
                            )
                            binding.pocketName.text =
                                viewPocketAndMenuItemViewIdMap[view.id]?.name?.toEditable()
                                    ?: "해당 주머니 이름을 모르겠습니다.".toEditable()
                            binding.pocketName.setSelection(binding.pocketName.text.toString().length)
                        }
                    }
                    if (viewModel.isPocketDeleteClicked.value == false && viewModel.isPocketRenameClicked.value == false) {
                        this.findViewById<TextView>(R.id.pocket_item_count).text =
                            mapOfPocketAndItemSize[it.value].toString()
                    }
                }
        }
    }

    // 아이템 툴팁 포지셔닝
    private fun updateToolTipViewPositioning(
        x: Float,
        y: Float,
        width: Int,
        height: Int
    ) {

        binding.editItemView.x = x - (binding.editItemView.width / 2) + (width / 2)
        binding.editSpeechBubble.x = x - (binding.editSpeechBubble.width / 3) + (width / 2)
        if (viewModel.isItemResizeClicked.value == true) {
            binding.editItemView.y =
                y - binding.editItemView.height - binding.editIncreaseHeight.height - viewDimenOfToolTip
        } else if (viewModel.isItemRecountClicked.value == true) {
            binding.editItemView.y =
                y - binding.editItemView.height - binding.editItemCountView.height - viewDimenOfToolTip
        } else if (viewModel.isItemRecolorClicked.value == true) {
            binding.editItemView.y =
                y - binding.editItemView.height - binding.editItemColorView.height - viewDimenOfToolTip
        } else {
            binding.editItemView.y = y - binding.editItemView.height - viewDimenOfToolTip
        }

        binding.editSpeechBubble.y = y - binding.editSpeechBubble.height

        binding.editItemColorView.x = x - (binding.editItemColorView.width / 2) + (width / 2)
        binding.editItemColorView.y = y - binding.editItemColorView.height - viewDimenOfToolTip

        binding.editItemCountView.x = x - (binding.editItemCountView.width / 2) + (width / 2)
        binding.editItemCountView.y = y - binding.editItemCountView.height - viewDimenOfToolTip

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
                    if (binding.drawerLayout.isOpen) {
                        binding.drawerLayout.close()
                    } else if (viewModel.isItemClicked.value == true) {
                        unSelectItem()
                        viewModel.itemIsUnClicked()
                    } else if (binding.searchView.isOpen) {
                        binding.searchView.closeSearch()
                    } else {
                        findNavController().navigateUp()
                    }
                }
            })

}