package com.turtle.amatda.data.repository.todo

import com.turtle.amatda.data.model.todo.TodoEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface TodoLocalDataSource {

    fun getTodoAll(): Flowable<List<TodoEntity>>
    fun insertTodo(todo: TodoEntity): Completable

}