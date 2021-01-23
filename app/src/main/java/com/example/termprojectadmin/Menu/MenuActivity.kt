package com.example.termprojectadmin.Menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.MenuItem
import com.example.termprojectadmin.R


class MenuActivity : AppCompatActivity() {
    lateinit var menuList: ArrayList<MenuItem>
    lateinit var edit_menu_recycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        menuList = MenuItem.createMenu()
        Log.d("Array", menuList.toString())
        Log.d("item1", menuList[0].price.toString())
        init()
        edit_menu_recycler.apply {
            layoutManager = GridLayoutManager(this@MenuActivity, 2)
            adapter = MenuAdapter(menuList,{position ->
                menuList.removeAt(position)
                fetchData(edit_menu_recycler)
            },{ position ->
                Toast.makeText(this@MenuActivity, "bruh", Toast.LENGTH_LONG).show()
            })
        }
    }

    private fun init() {
        edit_menu_recycler = findViewById(R.id.edit_menu_recycler)
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
    fun fetchData(recycler: RecyclerView){
        recycler.adapter?.notifyDataSetChanged()
    }
    fun showDialog(){

    }
}