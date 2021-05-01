package com.turtle.amatda.data.repository.todo

import com.turtle.amatda.data.db.todo.ToDoDao
import com.turtle.amatda.data.model.todo.TodoEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class TodoLocalDataSourceImpl @Inject constructor(private val todoDao: ToDoDao) : TodoLocalDataSource{

    override fun getTodoAll(): Flowable<List<TodoEntity>> {
        return todoDao.getAll()
    }

    override fun insertTodo(todo: TodoEntity): Completable {
        return todoDao.insert(todo)
    }


}