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

    //get snapshot of sales data in firebase
    fun getOption(): FirebaseRecyclerOptions<Sale> {
        val options = FirebaseRecyclerOptions.Builder<Sale>()
            .setQuery(queuery, Sale::class.java)
            .build()
        return options
    }

    //    reset sales quantity for all item
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

    //    write sales into quantity 0
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

    //    remove sales item
    fun removeValue(name: String){
        queuery.child(name).setValue(null)
    }
}