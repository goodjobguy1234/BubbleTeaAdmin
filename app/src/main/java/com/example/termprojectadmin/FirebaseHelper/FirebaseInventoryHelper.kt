package com.example.termprojectadmin.FirebaseHelper

import com.example.termprojectadmin.Entity.Inventory
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

object FirebaseInventoryHelper {
    private val firebaseInstance = FirebaseDatabase.getInstance()
    private var queuery = firebaseInstance.reference.child("inventory")

    fun getOption(): FirebaseRecyclerOptions<Inventory> {
        val options = FirebaseRecyclerOptions.Builder<Inventory>()
                .setQuery(queuery, Inventory::class.java)
                .build()
        return options
    }

    fun writeValue(item: Inventory){
        queuery.child(item.name).updateChildren(
                item.toMap()
        )
    }
}