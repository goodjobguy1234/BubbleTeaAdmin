package com.example.termprojectadmin.Queue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.Order
import com.example.termprojectadmin.OrderList
import com.example.termprojectadmin.R
import com.example.termprojectadmin.RecyclerItem

class QueueActivity : AppCompatActivity() {
    lateinit var queue_recycler: RecyclerView
    lateinit var remain_txt: TextView
    lateinit var total_txt: TextView
    lateinit var queueList:ArrayList<OrderList>
    lateinit var detailList: ArrayList<Order>
    lateinit var sectionList: ArrayList<RecyclerItem>
    lateinit var detail_recycler:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_queue)
        init()
//        queueList = OrderList.createQueue()
        total_txt.text = "0"
        sectionList = ArrayList()
        remain_txt.text = queueList.size.toString()
        detailList = ArrayList()
        queue_recycler.apply {
            layoutManager = LinearLayoutManager(this@QueueActivity)
            adapter = QueueAdapter(queueList, { position ->
                queueList.removeAt(position)
                queue_recycler.adapter!!.notifyDataSetChanged()
                remain_txt.text = queueList.size.toString()
                detailList.clear()
                sectionList.clear()
                total_txt.text = "0"
                detail_recycler.adapter!!.notifyDataSetChanged()
            }, { item ->
                val orderList = item.listOrder
                detailList.clear()
                sectionList.clear()
                detailList.addAll(orderList)
                total_txt.text = calculateTotal(detailList).toString()
                sectionList.addAll(transformList(detailList))
                detail_recycler.adapter!!.notifyDataSetChanged()
            })
        }
        detail_recycler.apply {
            layoutManager = LinearLayoutManager(this@QueueActivity)
            adapter = DetailAdapter(sectionList)
        }

    }
    override fun onStart() {
        super.onStart()
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    fun onClickBack(view: View) {
        finish()
    }
    fun init(){
        queue_recycler = findViewById(R.id.queue_recycler)
        remain_txt = findViewById(R.id.remain_txt)
        detail_recycler = findViewById(R.id.detail_recycler)
        total_txt = findViewById(R.id.queue_total_txt)
    }

    fun transformList(detailList: ArrayList<Order>):ArrayList<RecyclerItem>{
        val groupList = detailList.groupBy {
            it.reward
        }
        val myOrderList = ArrayList<RecyclerItem>()
        val myRedeemList = ArrayList<RecyclerItem>()
        for (i in groupList.keys){
            if (i){
                myRedeemList.add(RecyclerItem.Header("Reward Order"))
                for (v in groupList.getValue(i)){
                    myRedeemList.add(RecyclerItem.Product(v))
                }
            }else{
                myOrderList.add(RecyclerItem.Header("Order"))
                for (v in groupList.getValue(i)){
                    myOrderList.add(RecyclerItem.Product(v))
                }
            }
        }
        myOrderList.addAll(myRedeemList)


        return myOrderList
    }
    fun calculateTotal(detailList: ArrayList<Order>): Int{
        var total = 0
        detailList.forEach {
            if (!it.reward){
                total += (it.item.price * it.quantity)
            }
        }
        return total
    }




}