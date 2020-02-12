package edu.towson.cosc435.bajgai.todos_madhav

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_new_todo.*
import java.text.SimpleDateFormat
import edu.towson.cosc435.bajgai.todos_madhav.models.Todo

class NewTodoActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_todo)

        save_btn.setOnClickListener{ handleSaveTodoClick() }
    }


    var dateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm")

    private fun handleSaveTodoClick() {
        val intent = Intent()

        val id = -1
        val title = title_input.editableText.toString()
        val contents = contents_input.editableText.toString()
        val isCompleted = completeCheckBox.isChecked
        val image = "placeholder"
        val dateCreated = dateFormat.format((System.currentTimeMillis())).toString();


        //  val todo = TODO(title, contents, isCompleted, image, dateCreated)

        val todo = Todo(id, title, contents, isCompleted, image, dateCreated )



        val json = Gson().toJson(todo)
        intent.putExtra(TODO_EXTRA_KEY, json)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    companion object{
        val TODO_EXTRA_KEY = "TODO"
    }
}

