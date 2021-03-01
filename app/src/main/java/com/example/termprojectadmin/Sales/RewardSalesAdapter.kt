package com.example.termprojectadmin.Sales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.Entity.RewardSale
import com.example.termprojectadmin.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class RewardSalesAdapter(options: FirebaseRecyclerOptions<RewardSale>): FirebaseRecyclerAdapter<RewardSale, RewardSalesAdapter.ViewHolder>(options) {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title_txt = itemView.findViewById<TextView>(R.id.title_txt)
        val quantity_txt = itemView.findViewById<TextView>(R.id.sale_quantity_txt)
        val total_txt = itemView.findViewById<TextView>(R.id.sale_total_txt)

        fun bind(model: RewardSale){
            title_txt.text = model.name
            quantity_txt.text = model.quantity.toString()
            total_txt.text = calculateTotal(model).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sale_recycler_layout, parent, false)
        return ViewHolder(view)
    }

    private fun calculateTotal(model: RewardSale): Int{
        return model.price * model.quantity
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: RewardSale) {
        holder.bind(model)
    }

}