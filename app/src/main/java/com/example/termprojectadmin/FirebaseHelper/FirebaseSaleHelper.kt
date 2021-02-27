package com.example.termprojectadmin.FirebaseHelper

import com.example.termprojectadmin.Entity.Sale
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseSaleHelper {
    private val firebaseInstance = FirebaseDatabase.getInstance()
    private var queuery = firebaseInstance.reference.child("sale")
    fun getOption(): FirebaseRecyclerOptions<Sale> {
        val options = FirebaseRecyclerOptions.Builder<Sale>()
            .setQuery(queuery, Sale::class.java)
            .build()
        return options
    }

    fun resetSalesQuantity(){
        queuery.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val item = it.getValue(Sale::class.java)
                    writeValue(item!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun writeValue(item: Sale){
        queuery.child(item.name).setValue(
                Sale(
                        item.imageUrl,
                        item.name,
                        item.price,
                        0
                )
        )
    }

    fun removeValue(name: String){
        queuery.child(name).setValue(null)
    }
}