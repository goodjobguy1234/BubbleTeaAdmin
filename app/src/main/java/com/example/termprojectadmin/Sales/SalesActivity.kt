package com.example.termprojectadmin.Sales

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.BaseActivity
import com.example.termprojectadmin.Entity.RewardSale
import com.example.termprojectadmin.FirebaseHelper.FirebaseSaleHelper
import com.example.termprojectadmin.R
import com.example.termprojectadmin.Entity.Sale
import com.example.termprojectadmin.FirebaseHelper.FirebaseQueueHelper
import com.example.termprojectadmin.FirebaseHelper.FirebaseQueueIDHelper
import com.example.termprojectadmin.FirebaseHelper.FirebaseRewardHelper
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import java.text.SimpleDateFormat
import java.util.*

class SalesActivity : BaseActivity() {
    lateinit var sale_recycler: RecyclerView
    lateinit var reward_sale_recycler: RecyclerView
    lateinit var date_txt: TextView
    lateinit var reward_date_txt: TextView
    lateinit var saleList: FirebaseRecyclerOptions<Sale>
    lateinit var rewardSaleList: FirebaseRecyclerOptions<RewardSale>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        rewardSaleList = FirebaseRewardHelper.getOption()
        saleList = FirebaseSaleHelper.getOption()
        sale_recycler.apply {
            layoutManager = LinearLayoutManager(this@SalesActivity)
            adapter = SalesAdapter(saleList)
        }

        reward_sale_recycler.apply {
            layoutManager = LinearLayoutManager(this@SalesActivity)
            adapter = RewardSalesAdapter(rewardSaleList)
        }
    }

    private fun init() {
        sale_recycler = findViewById(R.id.sale_recycler)
        reward_sale_recycler = findViewById(R.id.reward_sale_recycler)
        date_txt = findViewById(R.id.date_txt)
        reward_date_txt = findViewById(R.id.reward_date_txt)
    }

    override fun onStart() {
        super.onStart()
        (sale_recycler.adapter as FirebaseRecyclerAdapter<*,*>).startListening()
        (reward_sale_recycler.adapter as FirebaseRecyclerAdapter<*,*>).startListening()
        setSales()

    }

    override fun onStop() {
        super.onStop()
        (sale_recycler.adapter as FirebaseRecyclerAdapter<*,*>).stopListening()
        (reward_sale_recycler.adapter as FirebaseRecyclerAdapter<*,*>).startListening()
    }

    override fun setLayoutResource(): Int {
        return R.layout.activity_sales
    }

    fun onClickBack(view: View) {
        finish()
    }

    fun setSales(){
        FirebaseQueueIDHelper.getRealtimeCurrentQueue { queue, date ->
            val currentDate = SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(Date())
            if (!date.equals(currentDate)){
                FirebaseQueueIDHelper.setQueue("A100", currentDate)
                FirebaseQueueHelper.resetValue()
                FirebaseSaleHelper.resetSalesQuantity()
                FirebaseRewardHelper.resetSalesQuantity()
            }
            date_txt.text = date
            reward_date_txt.text = date
        }
    }
}