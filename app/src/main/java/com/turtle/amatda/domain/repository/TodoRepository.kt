package com.turtle.amatda.domain.repository

import com.turtle.amatda.domain.model.Todo
import io.reactivex.Completable
import io.reactivex.Flowable

interface TodoRepository {
    fun getUserTodoAll() : Flowable<List<Todo>>
    fun insertTodo(todo: Todo) : Completable
}