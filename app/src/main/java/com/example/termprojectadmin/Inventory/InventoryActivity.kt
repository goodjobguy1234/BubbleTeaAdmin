package com.example.termprojectadmin.Inventory

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.termprojectadmin.BaseActivity
import com.example.termprojectadmin.Entity.Inventory
import com.example.termprojectadmin.FirebaseHelper.FirebaseInventoryHelper
import com.example.termprojectadmin.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class InventoryActivity : BaseActivity() {
    private lateinit var inventory_recycler: RecyclerView
    private lateinit var inventory: FirebaseRecyclerOptions<Inventory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        inventory = FirebaseInventoryHelper.getOption()
        inventory_recycler.apply {
            layoutManager = LinearLayoutManager(this@InventoryActivity)
            adapter = InventoryAdapter(inventory){item ->
                item?.let {
                    FirebaseInventoryHelper.writeValue(item)
                }?: showToast(this@InventoryActivity, "can not set below than 0")

            }
        }
    }

    // set up ui
    override fun setLayoutResource() = R.layout.activity_inventory

    // map variable with ui
    private fun init() {
        inventory_recycler = findViewById(R.id.inventory_recycler)
    }

    // when click at top left it go back to main page
    fun onClickBack(view: View) = finish()

    // when user click action button at bottom right to show dialog
    fun onClickAction(view: View) = showEditDialog(createEditDialog())

    override fun onStart() {
        super.onStart()
        (inventory_recycler.adapter as FirebaseRecyclerAdapter<*, *>).startListening()
    }

    override fun onStop() {
        super.onStop()
        (inventory_recycler.adapter as FirebaseRecyclerAdapter<*, *>).stopListening()
    }

    //  show edit dialog and push the input into firebase
    fun showEditDialog(dialog: AlertDialog){
        dialog.setOnShowListener {
            val edit = dialog.findViewById<EditText>(R.id.inventory_dialog_edt)

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).apply {
                setTextColor(Color.parseColor("#81B29A"))
                setOnClickListener {
                    val quantity = edit!!.text

                    if (quantity.isNotBlank() && quantity.isNotEmpty() && quantity.toString().toInt() > 0) {
                        inventory.snapshots.forEach {

                            with(it){
                                addRemainAmount(quantity.toString().toInt())
                                FirebaseInventoryHelper.writeValue(it)
                            }
                        }

                        dialog.dismiss()
                    }
                    else edit.error = "please input number"

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