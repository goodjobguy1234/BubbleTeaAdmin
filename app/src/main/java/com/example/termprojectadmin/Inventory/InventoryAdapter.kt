package com.example.termprojectadmin.Inventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.MenuItem
import com.example.termprojectadmin.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import java.util.zip.Inflater

class InventoryAdapter(options: FirebaseRecyclerOptions<MenuItem>, val callback: (MenuItem?) -> Unit)
    : FirebaseRecyclerAdapter<MenuItem, InventoryAdapter.ViewHolder>(options) {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val add_btn = itemView.findViewById<ImageButton>(R.id.add_btn)
        val subtract_btn = itemView.findViewById<ImageButton>(R.id.subtract_btn)
        val title = itemView.findViewById<TextView>(R.id.inventory_title_txt)
        val remain = itemView.findViewById<TextView>(R.id.inventory_remain_txt)
        fun bind(model: MenuItem){
            title.text = model.name
            remain.text = model.remain.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.inventory_recycler_layout, parent, false)
        return ViewHolder(view)
    }

    fun fetch(){
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: MenuItem) {
        holder.apply {
            bind(model)
            add_btn.setOnClickListener {
//                if (model.checkRemain()){
//                    model.addRemain()
//                    fetch()
//                }
                with(model){
                    addRemain()
                    fetch()
                    callback(this)
                }
            }
            subtract_btn.setOnClickListener {
                if (model.checkRemain()){
                    with(model){
                        subtractRemain()
                        fetch()
                        callback(this)
                    }
                }else{
                    callback(null)
                }
            }
        }
    }
}