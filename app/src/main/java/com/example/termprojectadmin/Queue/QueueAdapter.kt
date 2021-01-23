package com.example.termprojectadmin.Queue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.MenuItem
import com.example.termprojectadmin.OrderList
import com.example.termprojectadmin.R
import java.util.*
import kotlin.collections.ArrayList

class QueueAdapter(val queue: ArrayList<OrderList>, val callback: (position:Int) -> Unit, val onCLickLayout: (OrderList) -> Unit): RecyclerView.Adapter<QueueAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val queueNumber = itemView.findViewById<TextView>(R.id.queue_txt)
        val cancelBtn = itemView.findViewById<ImageButton>(R.id.cancel_btn)
        val card_layout = itemView.findViewById<CardView>(R.id.card_layout)
        fun bind(position: Int){
            queueNumber.text = queue[position].id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.queue_recycler_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bind(position)
            cancelBtn.setOnClickListener{
                callback(position)
            }
            card_layout.setOnClickListener {
                onCLickLayout(queue[position])
            }

        }
    }


    override fun getItemCount(): Int {
        return queue.size
    }



}