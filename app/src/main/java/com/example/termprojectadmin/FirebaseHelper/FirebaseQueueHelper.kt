package com.example.termprojectadmin.FirebaseHelper

import com.example.termprojectadmin.Entity.Queue
import com.example.termprojectadmin.Entity.Sale
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

object FirebaseQueueHelper {
    private val firebaseInstance = FirebaseDatabase.getInstance()
    private var queuery = firebaseInstance.reference.child("queue")

    fun getOption(): FirebaseRecyclerOptions<Queue> {
        val options = FirebaseRecyclerOptions.Builder<Queue>()
                .setQuery(queuery, Queue::class.java)
                .build()
        return options
    }

    fun removeValue(queueId:String){
        queuery.child(queueId).setValue(null)
    }

    fun resetValue(){
        queuery.ref.setValue(null)
    }
}