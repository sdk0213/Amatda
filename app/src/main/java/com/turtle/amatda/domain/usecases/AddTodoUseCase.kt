package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Todo
import com.turtle.amatda.domain.repository.TodoRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(private val repository: TodoRepository) {

    fun insertTodo(){
        CompositeDisposable().add(repository.insertTodo(Todo(0,"testTitle", "subTitle"))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        )
    }

}