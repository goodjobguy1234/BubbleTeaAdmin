package com.example.termprojectadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.termprojectadmin.Inventory.InventoryActivity
import com.example.termprojectadmin.Menu.MenuActivity
import com.example.termprojectadmin.Queue.QueueActivity
import com.example.termprojectadmin.Sales.SalesActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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