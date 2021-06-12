package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Todo
import com.turtle.amatda.domain.repository.TodoRepository
import com.turtle.amatda.domain.usecases.common.FlowableUseCase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(private val repository: TodoRepository) :
    FlowableUseCase<List<Todo>, Nothing>(Schedulers.io(), AndroidSchedulers.mainThread()) {

    override fun buildUseCaseCompletable(params: Nothing?): Flowable<List<Todo>> {
        return repository.getUserTodoAll()
    }

}