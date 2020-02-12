package edu.towson.cosc435.bajgai.todos_madhav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_view.view.*
import edu.towson.cosc435.bajgai.todos_madhav.models.Todo


class TodosAdapter(private val controller: ITodoController) : RecyclerView.Adapter<TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_view, parent, false)
        val viewHolder = TodoViewHolder(view)

        view.isCompletedCB.setOnClickListener {
            val position = viewHolder.adapterPosition
            controller.toggleComplete(position)
            this.notifyItemChanged(position)
        }

        view.setOnLongClickListener{
            val position = viewHolder.adapterPosition
            controller.deleteTodo(position)
            this.notifyItemRemoved(position)
            true
        }

        // edit fun when clicked on the to-do view
//        view.setOnClickListener {
//            val position = viewHolder.adapterPosition
//            controller.editTodo(position)
//            this.notifyItemChanged(position)
//        }

        return viewHolder

    }


    override fun getItemCount(): Int {
        return controller.todoCache.todoCount()
    }

//    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
//       val todo = controller.todoCache.getTodo(position)
//        holder.bindTodo(todo)
//    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = controller.todoCache.getTodo(position)
        holder.itemView.todoTitle.text = todo.title
        holder.itemView.todoContent.text = todo.content
        holder.itemView.icon.visibility = View.INVISIBLE
        holder.itemView.loader.visibility = View.VISIBLE
      //  holder.itemView.todoDate.text = todo.dateCreated
        holder.itemView.isCompletedCB.isChecked = todo.isCompleted

        controller.getIcon(todo.image, { icon ->
            if (holder.adapterPosition == position){
                holder.itemView.loader.visibility = View.INVISIBLE
                holder.itemView.icon.setImageBitmap(icon)
                holder.itemView.icon.visibility = View.VISIBLE
            }
        })
}
}


class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) //{
//    fun bindTodo(todo: Todo){
//        itemView.todoTitle.text = todo.title
//        itemView.todoContent.text = todo.content
//        itemView.todoDate.text = todo.dateCreated
//        itemView.isCompletedCB.isChecked = todo.isCompleted
//
//
//    }
//}