package com.example.termprojectadmin.Queue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.R
import com.example.termprojectadmin.RecyclerItem

const val TYPE_ORDER = 1
const val TYPE_REDEEM = 2
const val TYPE_HEADER = 0
class DetailAdapter(val order: ArrayList<RecyclerItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class DetailOrderHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val price_txt = itemView.findViewById<TextView>(R.id.edit_price_txt)
        val image = itemView.findViewById<ImageView>(R.id.imageView)
        val name_txt = itemView.findViewById<TextView>(R.id.name_txt)
        val quantity_txt = itemView.findViewById<TextView>(R.id.quantity_txt)
        fun bind(position: Int){
            price_txt.text = (order[position] as RecyclerItem.Product).order.item.price.toString()
            name_txt.text = (order[position] as RecyclerItem.Product).order.item.name
            quantity_txt.text = (order[position] as RecyclerItem.Product).order.quantity.toString()
        }
    }
    inner class DetailRedeemHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name_txt = itemView.findViewById<TextView>(R.id.name_txt)
        val quantity_txt = itemView.findViewById<TextView>(R.id.quantity_txt)
        val image = itemView.findViewById<ImageView>(R.id.imageView)
        fun bind(position: Int){
            name_txt.text = (order[position] as RecyclerItem.Product).order.item.name
            quantity_txt.text = (order[position] as RecyclerItem.Product).order.quantity.toString()
        }
    }

    inner class DetailHeaderHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val header_txt = itemView.findViewById<TextView>(R.id.header_txt)
        fun bind(position: Int){
            header_txt.text = (order[position] as RecyclerItem.Header).typeName
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            TYPE_ORDER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_order_layout, parent, false)
                DetailOrderHolder(view)
            }
            TYPE_REDEEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_redeem_layout, parent, false)
                DetailRedeemHolder(view)
            }
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_header_layout, parent, false)
                DetailHeaderHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_error_layout, parent, false)
                DetailHeaderHolder(view)
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType){
            TYPE_HEADER -> {
                (holder as DetailHeaderHolder).bind(position)
            }
            TYPE_REDEEM -> {
                (holder as DetailRedeemHolder).bind(position)
            }
            TYPE_ORDER -> {
                (holder as DetailOrderHolder).bind(position)
            }
        }
    }

    override fun getItemCount(): Int {
       return order.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(order[position]){
            is RecyclerItem.Header -> TYPE_HEADER
            is RecyclerItem.Product -> {
                if ((order[position] as RecyclerItem.Product).order.reward){
                    TYPE_REDEEM
                }else{
                    TYPE_ORDER
                }
            }
        }

    }
}