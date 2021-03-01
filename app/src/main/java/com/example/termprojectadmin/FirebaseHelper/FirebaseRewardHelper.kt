package com.example.termprojectadmin.FirebaseHelper

import com.example.termprojectadmin.Entity.RewardSale
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseRewardHelper {
    private val firebaseInstance = FirebaseDatabase.getInstance()
    private var queuery = firebaseInstance.reference.child("reward")

    fun getOption(): FirebaseRecyclerOptions<RewardSale> {
        val options = FirebaseRecyclerOptions.Builder<RewardSale>()
                .setQuery(queuery, RewardSale::class.java)
                .build()
        return options
    }

    fun resetSalesQuantity(){
       queuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val item = it.getValue(RewardSale::class.java)
                    writeValue(item!!)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun writeValue(item: RewardSale){
        queuery.child(item.name).setValue(
                RewardSale(
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