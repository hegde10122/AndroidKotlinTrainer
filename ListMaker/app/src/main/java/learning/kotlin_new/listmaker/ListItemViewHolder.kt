package learning.kotlin_new.listmaker

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!){

    val taskTextView = itemView?.findViewById<TextView>(R.id.textview_task) as TextView


}