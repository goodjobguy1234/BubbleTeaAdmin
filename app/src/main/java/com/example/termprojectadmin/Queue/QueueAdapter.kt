package com.example.termprojectadmin.Queue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.Entity.OrderList
import com.example.termprojectadmin.Entity.Queue
import com.example.termprojectadmin.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class QueueAdapter(val remain_txt: TextView,
                   options: FirebaseRecyclerOptions<Queue>,
                   val callback: (String) -> Unit,
                   val onCLickLayout: (Collection<OrderList>) -> Unit
): FirebaseRecyclerAdapter<Queue, QueueAdapter.ViewHolder>(options) {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val queueNumber = itemView.findViewById<TextView>(R.id.queue_txt)
        val cancelBtn = itemView.findViewById<ImageButton>(R.id.cancel_btn)
        val card_layout = itemView.findViewById<CardView>(R.id.card_layout)
        fun bind(model: Queue){
            queueNumber.text = model.queueId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.queue_recycler_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        remain_txt.text = itemCount.toString()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Queue) {
        holder.apply {
            bind(model)
            cancelBtn.setOnClickListener{
                callback(model.queueId)
            }
            card_layout.setOnClickListener {
                onCLickLayout(model.orderList.values)
            }
        }
        remain_txt.text = itemCount.toString()
    }


}