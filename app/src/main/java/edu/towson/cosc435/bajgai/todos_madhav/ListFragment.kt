package edu.towson.cosc435.bajgai.todos_madhav


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_list_layout.view.*


/**
 * A simple [Fragment] subclass.
 */
class ListFragment : Fragment(), View.OnClickListener {


    interface OnSelectChange {
        enum class Select { NONE, ALL, ACTIVE, COMPLETED}
        fun onSelectChange(select: Select)
    }

    private var currentSelect: OnSelectChange.Select = OnSelectChange.Select.NONE
    private var listener: OnSelectChange? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_layout, container, false)


        view.all_btn.setOnClickListener(this)
        view.active_btn.setOnClickListener(this)
        view.completed_btn.setOnClickListener(this)

        return view

    }

    fun setOnSelectChange(listener: OnSelectChange){
        this.listener = listener
    }

    fun getCurrentSelect(): OnSelectChange.Select {
        return currentSelect
    }
    override fun onClick(view: View) {

       currentSelect = when(view?.id){
            R.id.all_btn -> {
                OnSelectChange.Select.ALL
            }
            R.id.active_btn -> {
                OnSelectChange.Select.ACTIVE
            }
            R.id.completed_btn -> {
                OnSelectChange.Select.COMPLETED
            }
            else -> throw Exception("Unexpected view in onClick")
        }

        listener?.onSelectChange(currentSelect)


    }

}
