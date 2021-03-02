package com.example.termprojectadmin.FirebaseHelper

import com.example.termprojectadmin.Entity.MenuItem
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*

object FIrebaseMenuHelper {
    private val firebaseInstance = FirebaseDatabase.getInstance()
    private var queuery = firebaseInstance.reference.child("menu")

//    get snapshot of menu list in firebase
    fun getOption(): FirebaseRecyclerOptions<MenuItem> {
        val options = FirebaseRecyclerOptions.Builder<MenuItem>()
            .setQuery(queuery, MenuItem::class.java)
            .build()
        return options
    }

//    update
    fun writeValue(item: MenuItem){
        queuery.child(item.name).updateChildren(
            item.toMap()
        )
    }

//    remove
    fun removeValue(item: MenuItem){
        queuery.child(item.name).setValue(null)

    }
}