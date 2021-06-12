package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Todo
import com.turtle.amatda.domain.repository.TodoRepository
import com.turtle.amatda.domain.usecases.common.CompletableUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(private val repository: TodoRepository) :
    CompletableUseCase<Todo>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Todo?): Completable {
        return repository.insertTodo(params!!)
    }

}