package com.example.termprojectadmin.Queue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.BaseActivity
import com.example.termprojectadmin.Entity.OrderList
import com.example.termprojectadmin.Entity.Queue
import com.example.termprojectadmin.R
import com.example.termprojectadmin.Entity.RecyclerItem
import com.example.termprojectadmin.FirebaseHelper.FirebaseQueueHelper
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class QueueActivity : BaseActivity() {
    lateinit var queue_recycler: RecyclerView
    lateinit var remain_txt: TextView
    lateinit var total_txt: TextView
    lateinit var queueList: FirebaseRecyclerOptions<Queue>
    lateinit var detailList: ArrayList<OrderList>
    lateinit var sectionList: ArrayList<RecyclerItem>
    lateinit var detail_recycler:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        queueList = FirebaseQueueHelper.getOption()
        total_txt.text = "0"
        sectionList = ArrayList()
        detailList = ArrayList()

        queue_recycler.apply {
            layoutManager = LinearLayoutManager(this@QueueActivity)
            adapter = QueueAdapter(remain_txt, queueList, { queueId ->
                FirebaseQueueHelper.removeValue(queueId)
                remain_txt.text = (adapter as FirebaseRecyclerAdapter<*,*>).itemCount.toString()
                detailList.clear()
                sectionList.clear()
                total_txt.text = "0"
                detail_recycler.adapter!!.notifyDataSetChanged()
            }, { list ->
                detailList.clear()
                sectionList.clear()
                detailList.addAll(list)
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

    fun onClickBack(view: View) = finish()

    fun init(){
        queue_recycler = findViewById(R.id.queue_recycler)
        remain_txt = findViewById(R.id.remain_txt)
        detail_recycler = findViewById(R.id.detail_recycler)
        total_txt = findViewById(R.id.queue_total_txt)
    }

    fun transformList(detailList: ArrayList<OrderList>):ArrayList<RecyclerItem>{
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

    fun calculateTotal(detailList: ArrayList<OrderList>): Int{
        var total = 0
        detailList.forEach {
            if (!it.reward){
                total += (it.price * it.quantity)
            }
        }
        return total
    }

    override fun onStart() {
        super.onStart()
        (queue_recycler.adapter as FirebaseRecyclerAdapter<*, *>).startListening()
    }

    override fun setLayoutResource() = R.layout.activity_queue

    override fun onStop() {
        super.onStop()
        (queue_recycler.adapter as FirebaseRecyclerAdapter<*, *>).startListening()
    }



}