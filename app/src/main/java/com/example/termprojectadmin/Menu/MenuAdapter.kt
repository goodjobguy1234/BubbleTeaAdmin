package com.example.termprojectadmin.Menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.MenuItem
import com.example.termprojectadmin.R

class MenuAdapter(val menu: ArrayList<MenuItem>, val onDelete:(Int) -> Unit, val onEdit: (Int) -> Unit): RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val delete_btn = itemView.findViewById<Button>(R.id.delete_btn)
        val edit_btn = itemView.findViewById<Button>(R.id.edit_btn)
        val imageView = itemView.findViewById<ImageView>(R.id.menu_image)
        val name_txt = itemView.findViewById<TextView>(R.id.name_txt)
        val price = itemView.findViewById<TextView>(R.id.edit_price_txt)

        fun bind(position: Int){
            name_txt.text = menu[position].name
            price.text = menu[position].price.toString()
//            imageView.setImageResource(menu[position].imageId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_recycler_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            bind(position)
            delete_btn.setOnClickListener {
                onDelete(position)
            }
            edit_btn.setOnClickListener {
                onEdit(position)
            }

        }
    }

    override fun getItemCount(): Int {
        return menu.size
    }

}