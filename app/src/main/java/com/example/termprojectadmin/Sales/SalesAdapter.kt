package com.example.termprojectadmin.Sales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.R
import com.example.termprojectadmin.Sale

class SalesAdapter(val saleList: ArrayList<Sale>): RecyclerView.Adapter<SalesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title_txt = itemView.findViewById<TextView>(R.id.title_txt)
        val quantity_txt = itemView.findViewById<TextView>(R.id.sale_quantity_txt)
        val total_txt = itemView.findViewById<TextView>(R.id.sale_total_txt)
        fun bind(position: Int){
            title_txt.text = saleList[position].name
            quantity_txt.text = saleList[position].quantity.toString()
            total_txt.text = calculateTotal(position).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sale_recycler_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return saleList.size
    }

    private fun calculateTotal(position: Int): Int{
        return saleList[position].price * saleList[position].quantity
    }
}