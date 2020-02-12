package edu.towson.cosc435.bajgai.todos_madhav

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import edu.towson.cosc435.bajgai.todos_madhav.models.Todo

object TodoContract {
    object TodoEntry: BaseColumns {
        const val TABLE_NAME = "todos"
        const val COLUMN_NAME_TITLE = "todo_title"
        const val COLUMN_NAME_CONTENT = "todo_content"
        const val COLUMN_NAME_COMPLETED = "todo_completed"
        const val COLUMN_NAME_DELETED = "deleted"
    }
}

interface IDatabase {
    fun addTodo(todo: Todo)
    fun getTodos(): List<Todo>
    fun getTodo(id: Int): Todo?
    fun deleteTodo(todo: Todo)
    fun updateTodo(todo: Todo)

}

private const val CREATE_TODO_TABLE = "CREATE TABLE ${TodoContract.TodoEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
        "${TodoContract.TodoEntry.TABLE_NAME} TEXT, " +
        "${TodoContract.TodoEntry.COLUMN_NAME_TITLE} TEXT, " +
        "${TodoContract.TodoEntry.COLUMN_NAME_CONTENT} TEXT," +
        "${TodoContract.TodoEntry.COLUMN_NAME_COMPLETED} Bool, " +
        "${TodoContract.TodoEntry.COLUMN_NAME_DELETED} BOOL DEFAULT 0" +
        ")"

private const val DELETE_TODO_TABLE = "DROP TABLE IF EXISTS ${TodoContract.TodoEntry.TABLE_NAME}"

class TodosDatabase(ctx: Context): IDatabase {


    class TodoDbHelper(ctx: Context): SQLiteOpenHelper(ctx, DATABASE_NAME, null, DATABASE_VERSION ){
        override fun onCreate(db: SQLiteDatabase?) {
           db?.execSQL(CREATE_TODO_TABLE)
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL(DELETE_TODO_TABLE)
        }
    }

    companion object {
        val DATABASE_NAME = "todos.db"
        val DATABASE_VERSION = 1
    }

    private val db: SQLiteDatabase

    init {
        db = TodoDbHelper(ctx).writableDatabase
    }
    override fun addTodo(todo: Todo) {
        val cvs = toContentValues(todo)
        db.insert(TodoContract.TodoEntry.TABLE_NAME, null, cvs)
    }

    override fun getTodos(): List<Todo> {
        val projection = arrayOf(BaseColumns._ID, TodoContract.TodoEntry.COLUMN_NAME_TITLE, TodoContract.TodoEntry.COLUMN_NAME_CONTENT,
            TodoContract.TodoEntry.COLUMN_NAME_COMPLETED)
        val sortOrder = "${BaseColumns._ID} ASC"
        val selection = "${TodoContract.TodoEntry.COLUMN_NAME_DELETED} = ?"
        val selectionArg = arrayOf("0")

        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,
            projection,
            selection,
            selectionArg,
            null, // groupby
            null, // having
            sortOrder
        )
        val todos = mutableListOf<Todo>()
        with(cursor) {
            while(cursor.moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val title = getString(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_TITLE))
                val content = getString(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_CONTENT))
                val isCompleted = getInt(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_COMPLETED)) > 0
                val todo = Todo(id, title, content, isCompleted, "Place holder", "Nov 23 2019")
                todos.add(todo)
            }
        }
        return todos
    }

    override fun getTodo(id: Int): Todo? {
        val projection = arrayOf(BaseColumns._ID, TodoContract.TodoEntry.COLUMN_NAME_TITLE, TodoContract.TodoEntry.COLUMN_NAME_CONTENT,
            TodoContract.TodoEntry.COLUMN_NAME_COMPLETED)
        val sortOrder = "${BaseColumns._ID} ASC"
        val selection = "${TodoContract.TodoEntry.COLUMN_NAME_DELETED} = ?"
        val selectionArg = arrayOf("0", id.toString())

        val cursor = db.query(
            TodoContract.TodoEntry.TABLE_NAME,
            projection,
            selection,
            selectionArg,
            null, // groupby
            null, // having
            sortOrder
        )
        val todos = mutableListOf<Todo>()
        with(cursor) {
            while(cursor.moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val title = getString(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_TITLE))
                val content = getString(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_CONTENT))
                val isCompleted = getInt(getColumnIndex(TodoContract.TodoEntry.COLUMN_NAME_COMPLETED)) > 0
                val todo = Todo(id, title, content, isCompleted, "Place holder", "Nov 23 2019")
                todos.add(todo)
            }
        }
        if (todos.size == 1)
            return todos[0]
        return null
    }

    override fun deleteTodo(todo: Todo) {
        val cvs = toContentValues(todo)
        cvs.put(TodoContract.TodoEntry.COLUMN_NAME_DELETED, "1")
        val selection = " ${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(todo.id.toString())
        db.update(TodoContract.TodoEntry.TABLE_NAME, cvs, selection, selectionArgs)
    }

    override fun updateTodo(todo: Todo) {
        val cvs = toContentValues(todo)
        val selection = " ${BaseColumns._ID} = ?"
        val selectionArgs = arrayOf(todo.id.toString())
        db.update(TodoContract.TodoEntry.TABLE_NAME, cvs, selection, selectionArgs)
    }

    private fun toContentValues(todo: Todo): ContentValues {
        val cv = ContentValues()
        cv.put(TodoContract.TodoEntry.COLUMN_NAME_TITLE, todo.title)
        cv.put(TodoContract.TodoEntry.COLUMN_NAME_CONTENT, todo.content)
        cv.put(TodoContract.TodoEntry.COLUMN_NAME_COMPLETED, todo.isCompleted)
        return cv
    }

}