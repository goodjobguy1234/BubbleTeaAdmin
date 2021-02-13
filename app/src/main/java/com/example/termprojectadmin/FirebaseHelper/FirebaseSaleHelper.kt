package com.example.termprojectadmin.FirebaseHelper

import com.example.termprojectadmin.Sale
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

object FirebaseSaleHelper {
    private val firebaseInstance = FirebaseDatabase.getInstance()
    private var queuery = firebaseInstance.reference.child("sale")
    fun getOption(): FirebaseRecyclerOptions<Sale> {
        val options = FirebaseRecyclerOptions.Builder<Sale>()
            .setQuery(queuery, Sale::class.java)
            .build()
        return options
    }
}