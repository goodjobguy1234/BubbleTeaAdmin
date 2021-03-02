package com.example.termprojectadmin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.termprojectadmin.Inventory.InventoryActivity
import com.example.termprojectadmin.Menu.MenuActivity
import com.example.termprojectadmin.Queue.QueueActivity
import com.example.termprojectadmin.Sales.SalesActivity
import java.text.DateFormat
import java.util.*

class MainActivity : BaseActivity() {
    lateinit var date_txt:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        date_txt = findViewById(R.id.date_txt)
        date_txt.text = getDate()
    }

//    get current date from android system
    private fun getDate(): CharSequence? {
        val dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.US)
        return   dateFormat.format(Date())
    }

    override fun setLayoutResource() = R.layout.activity_main

    //    when user click queueing go to queueing page
    fun onClickQueue(view:View) = startActivity(Intent(this, QueueActivity::class.java))

    //    when user click menu go to edit menu page
    fun onClickMenu(view:View) = startActivity(Intent(this, MenuActivity::class.java))

    //    when user click sales go to sale summary page
    fun onClickSales(view:View) = startActivity(Intent(this, SalesActivity::class.java))

    //    when user click inventory go to inventory page
    fun onClickInventory(view:View) = startActivity(Intent(this, InventoryActivity::class.java))
}