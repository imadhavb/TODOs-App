package edu.towson.cosc435.bajgai.todos_madhav

import android.graphics.Bitmap

interface ITodoController {


    fun toggleComplete(idx: Int)
    fun launchNewTodoScreen()
   // fun editTodo(idx: Int)
    fun deleteTodo(idx: Int)
    val todoCache: ITodoCache

    fun getIcon(iconUrl: String, callback: (Bitmap) -> Unit)

}

