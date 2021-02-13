package com.example.termprojectadmin.Sales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.BaseActivity
import com.example.termprojectadmin.FirebaseHelper.FirebaseSaleHelper
import com.example.termprojectadmin.R
import com.example.termprojectadmin.Sale
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class SalesActivity : BaseActivity() {
    lateinit var sale_recycler: RecyclerView
    lateinit var saleList: FirebaseRecyclerOptions<Sale>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        saleList = FirebaseSaleHelper.getOption()
        sale_recycler.apply {
            layoutManager = LinearLayoutManager(this@SalesActivity)
            adapter = SalesAdapter(saleList)
            fetch(sale_recycler)
        }
    }

    private fun init() {
        sale_recycler = findViewById(R.id.sale_recycler)
    }

    override fun onStart() {
        super.onStart()
        (sale_recycler.adapter as FirebaseRecyclerAdapter<*,*>).startListening()
    }

    override fun onStop() {
        super.onStop()
        (sale_recycler.adapter as FirebaseRecyclerAdapter<*,*>).stopListening()
    }

    override fun setLayoutResource(): Int {
        return R.layout.activity_sales
    }

    fun onClickBack(view: View) {
        finish()
    }

    fun fetch(recycler: RecyclerView){
        recycler.adapter?.notifyDataSetChanged()
    }
}