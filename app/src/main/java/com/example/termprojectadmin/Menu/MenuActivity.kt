package com.example.termprojectadmin.Menu

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
                showDialog(createDialog(), menuList[position])
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
    fun createDialog(): AlertDialog{
        val view = layoutInflater.inflate(R.layout.dialog_custom_layout, null)
        val dialog = AlertDialog.Builder(this).apply {
            setView(view)
            setCancelable(false)
            setPositiveButton("Confirm") { _, _ ->
            }
            setNegativeButton("Cancel") { _, _ ->
            }
        }.create()
        return  dialog
    }
    fun showDialog(dialog: AlertDialog, menu: MenuItem){
        dialog.setOnShowListener {
            val nameEdit = dialog.findViewById<EditText>(R.id.name_edt)
            val priceEdit = dialog.findViewById<EditText>(R.id.price_edt)
            val image = dialog.findViewById<ImageView>(R.id.dialog_imageView)
            nameEdit!!.setText(menu.name)
            priceEdit!!.setText(menu.price.toString())
            image!!.setImageResource(menu.imageId)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).apply {
                setTextColor(Color.parseColor("#81B29A"))
                setOnClickListener {
                    val position = menuList.indexOf(menu)
                    menuList[position].name = nameEdit.text.toString()
                    menuList[position].price = priceEdit.text.toString().toInt()
                    dialog.dismiss()
                    fetchData(edit_menu_recycler)
                }
            }
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).apply {
                setTextColor(resources.getColor(R.color.button))
                setOnClickListener {
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }
}