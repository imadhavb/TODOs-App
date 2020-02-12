package edu.towson.cosc435.bajgai.todos_madhav

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import edu.towson.cosc435.bajgai.todos_madhav.models.Todo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.todo_view.*


class MainActivity : AppCompatActivity(), ITodoController, ListFragment.OnSelectChange {
    override fun getIcon(iconUrl: String, callback: (Bitmap) -> Unit) {
        NetworkHelper()
            .fetchIcon(iconUrl, callback)
    }

    val receiver: BroadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(ctx: Context?, intent: Intent?) {
            Log.e("tag", "MSG Received")
        }
    }

    override fun launchNewTodoScreen() {
        val intent = Intent(this, NewTodoActivity::class.java)
        startActivityForResult(intent, ADD_TODO_REQUEST_CODE)
    }

    override fun toggleComplete(idx: Int) {
        val todo = todoCache.getTodo(idx)
        val newTodo = todo.copy(isCompleted = !todo.isCompleted)
        todosDB.update(newTodo)
        todoCache.refresh(todosDB.getAll())
    }

    override fun deleteTodo(idx: Int) {
        val current = todoCache.getTodo(idx)
        todosDB.remove(current)
        todoCache.refresh(todosDB.getAll())
    }

    lateinit var todosDB: ITodoRepository
    override lateinit var todoCache: ITodoCache


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val frag = supportFragmentManager
            .findFragmentById(R.id.listTypeFragment)

        if (frag != null)
            if (frag is ListFragment){
                frag.setOnSelectChange(this)
            }

       todosDB = TodoDBRepository(this)
       todoCache = TodoCache()
       todoCache.refresh(todosDB.getAll())

        val adapter = TodosAdapter(this)

        recyclerView.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)

        New_Activity_BTN.setOnClickListener { launchNewTodoScreen() }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){
            Activity.RESULT_OK -> {
                when(requestCode) {
                    ADD_TODO_REQUEST_CODE -> {
                        val json: String? = data?.getStringExtra(NewTodoActivity.TODO_EXTRA_KEY)

                      //  if (json != null){
                            val todo: Todo = Gson().fromJson<Todo>(json, Todo::class.java)

                            todosDB.addTodo(todo)
                            todoCache.refresh(todosDB.getAll())
                       // }
                    }
                }
            }
            Activity.RESULT_CANCELED -> {
                Toast.makeText(this, getString(R.string.userCanceled), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        val ADD_TODO_REQUEST_CODE = 1
    }

    override fun onSelectChange(select: ListFragment.OnSelectChange.Select) {

            // will implement later
        when(select){
            ListFragment.OnSelectChange.Select.ALL -> {
                Toast.makeText(this, "All selected", Toast.LENGTH_SHORT).show()
            }
            ListFragment.OnSelectChange.Select.ACTIVE -> {
                Toast.makeText(this, "ACTIVE selected", Toast.LENGTH_SHORT).show()
            }
            ListFragment.OnSelectChange.Select.COMPLETED -> {
                Toast.makeText(this, "COMPLETED selected", Toast.LENGTH_SHORT).show()
            }
           else -> throw Exception ("Error")
        }
    }
}

