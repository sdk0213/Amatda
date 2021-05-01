package com.turtle.amatda.domain.usecases

import com.turtle.amatda.domain.model.Todo
import com.turtle.amatda.domain.repository.TodoRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetTodoUseCase @Inject constructor(private val repository: TodoRepository) {

    fun getTodoAll() : Flowable<List<Todo>> {
        return repository.getUserTodoAll()
    }

}