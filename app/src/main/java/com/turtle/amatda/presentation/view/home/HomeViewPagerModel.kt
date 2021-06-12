package com.turtle.amatda.presentation.view.home

import com.turtle.amatda.domain.usecases.SelectItemUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import javax.inject.Inject

class HomeViewPagerModel @Inject constructor(
    private val settingItemUseCase: SelectItemUseCase
    ) : BaseViewModel() {

}