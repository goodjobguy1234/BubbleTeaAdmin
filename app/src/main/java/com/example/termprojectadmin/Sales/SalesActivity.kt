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
    private lateinit var sale_recycler: RecyclerView
    private lateinit var reward_sale_recycler: RecyclerView
    private lateinit var date_txt: TextView
    private lateinit var reward_date_txt: TextView
    private lateinit var saleList: FirebaseRecyclerOptions<Sale>
    private lateinit var rewardSaleList: FirebaseRecyclerOptions<RewardSale>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        rewardSaleList = FirebaseRewardHelper.getOption()
        saleList = FirebaseSaleHelper.getOption()

//        display sale list
        sale_recycler.apply {
            layoutManager = LinearLayoutManager(this@SalesActivity)
            adapter = SalesAdapter(saleList)
        }

//        display reward list
        reward_sale_recycler.apply {
            layoutManager = LinearLayoutManager(this@SalesActivity)
            adapter = RewardSalesAdapter(rewardSaleList)
        }
    }

//    map variable with ui
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

//    set up ui
    override fun setLayoutResource() = R.layout.activity_sales

//    when user click back button at top left then go back to main page
    fun onClickBack(view: View) = finish()

    /*setup sales data check that  if date is on current then set text as that date
    * if not then reset queue id, delete all queue list and reset sales and reward sales data
    * */
    private fun setSales(){
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