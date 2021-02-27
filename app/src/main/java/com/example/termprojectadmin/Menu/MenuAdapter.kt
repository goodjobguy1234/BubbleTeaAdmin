package com.example.termprojectadmin.Menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.termprojectadmin.Entity.MenuItem
import com.example.termprojectadmin.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MenuAdapter(options: FirebaseRecyclerOptions<MenuItem>,
                  val onDelete:(MenuItem) -> Unit,
                  val onEdit: (MenuItem) -> Unit): FirebaseRecyclerAdapter<MenuItem, MenuAdapter.ViewHolder>(options) {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val delete_btn = itemView.findViewById<Button>(R.id.delete_btn)
        val edit_btn = itemView.findViewById<Button>(R.id.edit_btn)
        val imageView = itemView.findViewById<ImageView>(R.id.menu_image)
        val name_txt = itemView.findViewById<TextView>(R.id.name_txt)
        val price = itemView.findViewById<TextView>(R.id.edit_price_txt)

        fun bind(model: MenuItem){
            name_txt.text = model.name
            price.text = model.price.toString()
            with(imageView){
                Glide.with(this).load(model.imageUrl).into(this)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_recycler_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: MenuItem) {
        holder.apply {
            bind(model)
            delete_btn.setOnClickListener {
                onDelete(model)
            }
            edit_btn.setOnClickListener {
                onEdit(model)
            }
        }
    }

}