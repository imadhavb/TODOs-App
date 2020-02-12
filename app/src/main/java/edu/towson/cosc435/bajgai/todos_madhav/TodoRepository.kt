package edu.towson.cosc435.bajgai.todos_madhav

import edu.towson.cosc435.bajgai.todos_madhav.models.Todo

class TodoCache: ITodoCache {
    override fun todoCount(): Int {
       return todos.size
    }

    override fun getTodo(position: Int): Todo {
        return todos.get(position)
    }

    override fun refresh(todos: List<Todo>) {
       this.todos.clear()
        this.todos.addAll(todos)
    }

    private var todos: MutableList<Todo> = mutableListOf()


}