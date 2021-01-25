package com.example.termprojectadmin.Inventory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.MenuItem
import com.example.termprojectadmin.R
import java.util.zip.Inflater

class InventoryAdapter(var inventory: ArrayList<MenuItem>): RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val add_btn = itemView.findViewById<ImageButton>(R.id.add_btn)
        val subtract_btn = itemView.findViewById<ImageButton>(R.id.subtract_btn)
        val title = itemView.findViewById<TextView>(R.id.inventory_title_txt)
        val remain = itemView.findViewById<TextView>(R.id.inventory_remain_txt)
        fun bind(position:Int){
            title.text = inventory[position].name
            remain.text = inventory[position].remainder.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.inventory_recycler_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bind(position)
            add_btn.setOnClickListener {
                inventory[position].remainder++
                fetch()
            }
            subtract_btn.setOnClickListener {
                inventory[position].remainder--
                fetch()
            }
        }
    }

    override fun getItemCount(): Int {
        return inventory.size
    }

    fun fetch(){
        notifyDataSetChanged()
    }
}