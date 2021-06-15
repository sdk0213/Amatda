package com.turtle.amatda.presentation.view.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.toLiveData
import com.turtle.amatda.domain.model.Todo
import com.turtle.amatda.domain.usecases.AddTodoUseCase
import com.turtle.amatda.domain.usecases.GetTodoUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TodoViewModel @Inject constructor(
    private val addTodoUseCase: AddTodoUseCase,
    private val getTodoUseCase: GetTodoUseCase
) : BaseViewModel() {

    fun getTodo(): LiveData<List<Todo>> {
        val getTodoUseCase = getTodoUseCase.execute()
        compositeDisposable.add(getTodoUseCase
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())

        return getTodoUseCase.toLiveData()

    }

}
