package com.example.termprojectadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.termprojectadmin.Inventory.InventoryActivity
import com.example.termprojectadmin.Menu.MenuActivity
import com.example.termprojectadmin.Queue.QueueActivity
import com.example.termprojectadmin.Sales.SalesActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var date_txt:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        date_txt = findViewById(R.id.date_txt)
        date_txt.text = getDate()
    }

    private fun getDate(): CharSequence? {
        val dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.US)
        return   dateFormat.format(Date())
    }

    override fun onStart() {
        super.onStart()
        window.decorView.apply {
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }
    fun onClickQueue(view:View){
        startActivity(Intent(this, QueueActivity::class.java))
    }
    fun onClickMenu(view:View){
        startActivity(Intent(this, MenuActivity::class.java))
    }
    fun onClickSales(view:View){
        startActivity(Intent(this, SalesActivity::class.java))
    }
    fun onClickInventory(view:View){
        startActivity(Intent(this, InventoryActivity::class.java))
    }
}