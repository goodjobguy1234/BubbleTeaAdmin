package com.example.termprojectadmin.Inventory

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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



    fun onClickBack(view: View) {
        finish()
    }

    fun onClickAction(view: View) {
        showEditDialog(createEditDialog())

    }
    fun createEditDialog(): AlertDialog{
        val view = layoutInflater.inflate(R.layout.inventory_dialog_layout, null)
        val dialog = AlertDialog.Builder(this).apply {
            setView(view)
            setCancelable(false)
            setPositiveButton("Confirm") { _, _ ->
            }
            setNegativeButton("Cancel") { _, _ ->
            }
        }.create()
        return dialog
    }
    fun showEditDialog(dialog: AlertDialog){
        dialog.setOnShowListener {
            val edit = dialog.findViewById<EditText>(R.id.inventory_dialog_edt)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).apply {
                setTextColor(Color.parseColor("#81B29A"))
                setOnClickListener {
                    val quantity = edit!!.text
                    //do smt
                    if (quantity.isNotBlank() && quantity.isNotEmpty() && quantity.toString().toInt() > 0) {
                        inventory.forEach {
                            it.addRemainAmount(quantity.toString().toInt())
                        }
                        inventory_recycler.adapter!!.notifyDataSetChanged()
                        dialog.dismiss()
                    }else{
                        edit.error = "Please input number"
                    }
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

    override fun onStart() {
        super.onStart()
        setUpLayout()
    }

    fun setUpLayout(){
        window.decorView.apply {
            systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        setUpLayout()
    }
}