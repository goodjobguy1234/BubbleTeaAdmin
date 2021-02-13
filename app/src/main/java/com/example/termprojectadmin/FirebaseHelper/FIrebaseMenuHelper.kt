package com.example.termprojectadmin.FirebaseHelper

import com.example.termprojectadmin.Entity.MenuItem
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

const val SUBTRACT = 0
const val ADD = 1
const val UPDATE = 2

object FIrebaseMenuHelper {
    private val firebaseInstance = FirebaseDatabase.getInstance()
    private var queuery = firebaseInstance.reference.child("menu")
    fun getOption(): FirebaseRecyclerOptions<MenuItem> {
        val options = FirebaseRecyclerOptions.Builder<MenuItem>()
            .setQuery(queuery, MenuItem::class.java)
            .build()
        return options
    }
    fun updateRemain(menuname: String, TYPE: Int, callback: ((MenuItem?) -> Unit)? = null){
        queuery.orderByKey().equalTo(menuname).limitToFirst(1)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                       snapshot.children.first().getValue(MenuItem::class.java).let{
                           if (!it!!.checkRemain() && TYPE == SUBTRACT){
                               callback?.invoke(null)
                           }
                           if(it.checkRemain() && TYPE == SUBTRACT){
                               callback?.invoke(it)
                               it.subtractRemain()
                           }
                           if (TYPE == ADD){
                               it.addRemain()
                           }
                           it.toMap()
                       }.run {
                           queuery.child(menuname).setValue(this)
                       }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
    }

    fun updateRemainAmount(menuname:String, amount:Int){
        val queue = queuery.orderByKey().equalTo(menuname).limitToFirst(1)
        queue.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children){
                    val menu = i.getValue(MenuItem::class.java)
                    menu!!.addRemainAmount(amount)
                    queuery.child(menuname).setValue(menu.toMap())
                }


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
    fun writeValue(item: MenuItem){
        queuery.child(item.name).updateChildren(
            item.toMap()
        )
    }

    }