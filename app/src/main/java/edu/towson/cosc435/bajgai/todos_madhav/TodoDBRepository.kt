package edu.towson.cosc435.bajgai.todos_madhav

import android.content.Context
import edu.towson.cosc435.bajgai.todos_madhav.models.Todo

class TodoDBRepository(ctx: Context) : ITodoRepository {

    private val db: IDatabase

    init {
        db = TodosDatabase(ctx)
    }

    override fun getCount(): Int {
        return db.getTodos().size
    }

    override fun getTodo(idt: Int): Todo? {
        return db.getTodo(idt)
    }

    override fun getAll(): List<Todo> {
        return db.getTodos()
    }

    override fun remove(todo: Todo) {
        db.deleteTodo(todo)
    }

    override fun update(todo: Todo) {
        db.updateTodo(todo)
    }

    override fun addTodo(todo: Todo) {
        db.addTodo(todo)
    }

}