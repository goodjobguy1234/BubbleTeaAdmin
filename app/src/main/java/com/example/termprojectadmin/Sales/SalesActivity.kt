package com.example.termprojectadmin.Sales

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.R
import com.example.termprojectadmin.Sale

class SalesActivity : AppCompatActivity() {
    lateinit var sale_recycler: RecyclerView
    lateinit var saleList: ArrayList<Sale>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        init()
        saleList = Sale.createSales()
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
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    fun onClickBack(view: View) {
        finish()
    }

    fun fetch(recycler: RecyclerView){
        recycler.adapter?.notifyDataSetChanged()
    }
}