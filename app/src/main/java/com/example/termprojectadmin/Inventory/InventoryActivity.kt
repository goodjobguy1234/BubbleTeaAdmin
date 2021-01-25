package com.example.termprojectadmin.Inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.MenuItem
import com.example.termprojectadmin.R

class InventoryActivity : AppCompatActivity() {
    lateinit var inventory_recycler: RecyclerView
    lateinit var inventory: ArrayList<MenuItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)
        init()
        inventory = MenuItem.createMenu()
        inventory_recycler.apply {
            layoutManager = LinearLayoutManager(this@InventoryActivity)
            adapter = InventoryAdapter(inventory)
        }
    }

    private fun init() {
        inventory_recycler = findViewById(R.id.inventory_recycler)
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

    fun onClickAction(view: View) {


    }
    fun showEditDialog(){

    }
}