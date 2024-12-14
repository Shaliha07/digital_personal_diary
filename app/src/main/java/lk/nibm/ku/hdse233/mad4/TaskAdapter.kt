package lk.nibm.ku.hdse233.mad4

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class TaskAdapter(
    private val context: Context,
    private val taskList: MutableList<Task>,
    private val onUpdate: (Task) -> Unit,
    private val onDelete: (Int) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = taskList.size

    override fun getItem(index: Int): Task = taskList[index]

    override fun getItemId(index: Int): Long = taskList[index].id.toLong()

    override fun getView(index: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.taskview, parent, false)
            viewHolder = ViewHolder(
                view.findViewById(R.id.lbltitle),
                view.findViewById(R.id.lbldate),
                view.findViewById(R.id.btnupdate),
                view.findViewById(R.id.btndelete)
            )
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val task = taskList[index]
        viewHolder.lblTitle.text = task.notes
        viewHolder.lblDate.text = task.memos

        viewHolder.btnUpdate.setOnClickListener { onUpdate(task) }
        viewHolder.btnDelete.setOnClickListener { onDelete(task.id) }

        return view
    }

    private data class ViewHolder(
        val lblTitle: TextView,
        val lblDate: TextView,
        val btnUpdate: Button,
        val btnDelete: Button
    )
}
