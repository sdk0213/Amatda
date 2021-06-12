package com.turtle.amatda.presentation.view.itemselect

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.turtle.amatda.domain.model.Item
import com.turtle.amatda.domain.repository.ItemRepository
import com.turtle.amatda.domain.usecases.SelectItemUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ItemSelectViewModel @Inject constructor(
    private val selectItemUseCase: SelectItemUseCase
    ): BaseViewModel() {

    fun getItem() : LiveData<List<Item>> {
        val selectItemUseCase = selectItemUseCase.execute()
        compositeDisposable.add(selectItemUseCase
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())

        return selectItemUseCase.toLiveData()
    }
}