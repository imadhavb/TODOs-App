package edu.towson.cosc435.bajgai.todos_madhav

import edu.towson.cosc435.bajgai.todos_madhav.models.Todo


interface ITodoRepository {

    fun getCount(): Int
    fun getTodo(idx: Int): Todo?
    fun getAll(): List<Todo>
    fun remove(todo: Todo)
    fun update(todo: Todo)
    fun addTodo(todo: Todo)
}

interface ITodoCache {
    fun todoCount(): Int
    fun getTodo(position: Int): Todo
    fun refresh(todos: List<Todo>)
}